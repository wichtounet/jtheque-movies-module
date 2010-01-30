package org.jtheque.movies.persistence.dao.impl;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.managers.persistence.GenericDao;
import org.jtheque.core.managers.persistence.Query;
import org.jtheque.core.managers.persistence.QueryMapper;
import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.core.managers.persistence.context.IDaoPersistenceContext;
import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.MovieCategoryRelation;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.primary.dao.able.IDaoCollections;
import org.jtheque.primary.od.able.Data;
import org.jtheque.utils.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A Data Access Object implementation for movies.
 *
 * @author Baptiste Wicht
 */
public final class DaoMovies extends GenericDao<Movie> implements IDaoMovies {
    private final ParameterizedRowMapper<Movie> rowMapper = new MovieRowMapper();
    private final ParameterizedRowMapper<MovieCategoryRelation> relationRowMapper = new RelationRowMapper();
    private final QueryMapper queryMapper = new MovieQueryMapper();

    private Collection<MovieCategoryRelation> relationsToCategories;

    @Resource
    private IDaoPersistenceContext daoPersistenceContext;

    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    @Resource
    private IDaoCollections daoCollections;

    @Resource
    private IDaoCategories daoCategories;

	/**
     * Construct a new DaoMovies.
     */
    public DaoMovies(){
        super(TABLE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Movie> getMovies(){
        List<Movie> films = (List<Movie>) getMovies(daoCollections.getCurrentCollection());

        Collections.sort(films);

        return films;
    }

    /**
     * Return all the films of the collection.
     *
     * @param collection The collection.
     * @return A List containing all the films of the collections.
     */
    private Collection<? extends Data> getMovies(org.jtheque.primary.od.able.Collection collection){
        if (collection == null || !collection.isSaved()){
            return getAll();
        }

        load();

        Collection<Movie> movies = new ArrayList<Movie>(getCache().size() / 2);

        for (Movie movie : getCache().values()){
            if (movie.isInCollection(collection)){
                movies.add(movie);
            }
        }

        return movies;
    }

    @Override
    public boolean delete(Movie movie){
        jdbcTemplate.update("DELETE FROM " + MOVIES_CATEGORIES_TABLE + " WHERE THE_MOVIE_FK = ?", movie.getId());
        
        return super.delete(movie);
    }

    @Override
    public Movie getMovie(int id){
        return get(id);
    }

	@Override
	public Movie getMovie(String title){
        for (Movie movie : getMovies()){
            if (movie.getTitle().equals(title)){
                return movie;
            }
        }

        return null;
	}

	@Override
    public void save(Movie movie){
        super.save(movie);

        jdbcTemplate.update("DELETE FROM " + MOVIES_CATEGORIES_TABLE + " WHERE THE_MOVIE_FK = ?", movie.getId());

        createLinks(movie);
    }

    /**
     * Create the links between the movie and the categories.
     *
     * @param movie The movie.
     */
    private void createLinks(Movie movie){
        for (Category category : movie.getCategories()){
            jdbcTemplate.update("INSERT INTO " + MOVIES_CATEGORIES_TABLE + " (THE_MOVIE_FK, THE_CATEGORY_FK) VALUES(?,?)", movie.getId(), category.getId());
        }
    }

    @Override
    public void create(Movie movie){
        movie.setTheCollection(daoCollections.getCurrentCollection());

        //First we create the movie in the table
        super.create(movie);

        //next, we can create the links to the categories
        createLinks(movie);
    }

    @Override
    public Movie createMovie(){
        return new MovieImpl();
    }

    @Override
    protected ParameterizedRowMapper<Movie> getRowMapper(){
        return rowMapper;
    }

    @Override
    protected QueryMapper getQueryMapper(){
        return queryMapper;
    }

    @Override
    protected void loadCache(){
        relationsToCategories = jdbcTemplate.query("SELECT * FROM " + MOVIES_CATEGORIES_TABLE, relationRowMapper);

        Collection<Movie> movies = daoPersistenceContext.getSortedList(TABLE, rowMapper);

        for (Movie movie : movies){
            getCache().put(movie.getId(), movie);
        }

        setCacheEntirelyLoaded();

        relationsToCategories.clear();
    }

    @Override
    protected void load(int i){
        Movie book = daoPersistenceContext.getDataByID(TABLE, i, rowMapper);

        getCache().put(i, book);
    }

    /**
     * A row mapper to map a resultset to a relation between category and movie.
     *
     * @author Baptiste Wicht
     */
    private static final class RelationRowMapper implements ParameterizedRowMapper<MovieCategoryRelation> {
        @Override
        public MovieCategoryRelation mapRow(ResultSet rs, int i) throws SQLException{
            MovieCategoryRelation relation = new MovieCategoryRelation();

            relation.setMovie(rs.getInt("THE_MOVIE_FK"));
            relation.setCategory(rs.getInt("THE_CATEGORY_FK"));

            return relation;
        }
    }

    /**
     * A row mapper to map a resultset to a movie.
     *
     * @author Baptiste Wicht
     */
    private final class MovieRowMapper implements ParameterizedRowMapper<Movie> {
        @Override
        public Movie mapRow(ResultSet rs, int i) throws SQLException{
            Movie movie = createMovie();

            movie.setId(rs.getInt("ID"));
            movie.setTitle(rs.getString("TITLE"));
            movie.setFile(rs.getString("FILE"));
            movie.setImage(rs.getString("IMAGE"));
			movie.setResolution(new Resolution(rs.getString("RESOLUTION")));
			movie.setDuration(new PreciseDuration(rs.getLong("DURATION")));
            movie.setTheCollection(daoCollections.getCollection(rs.getInt("THE_COLLECTION_FK")));

            if (StringUtils.isNotEmpty(rs.getString("NOTE"))){
                movie.setNote(DaoNotes.getInstance().getNote(DaoNotes.NoteType.getEnum(rs.getInt("NOTE"))));
            }

            if (relationsToCategories != null && !relationsToCategories.isEmpty()){
                for (MovieCategoryRelation relation : relationsToCategories){
                    if (relation.getMovie() == movie.getId()){
                        movie.addCategory(daoCategories.getCategory(relation.getCategory()));
                    }
                }
            } else {
                relationsToCategories = jdbcTemplate.query("SELECT * FROM " + MOVIES_CATEGORIES_TABLE + " WHERE THE_MOVIE_FK = ?", relationRowMapper, movie.getId());

                for (MovieCategoryRelation relation : relationsToCategories){
                    movie.addCategory(daoCategories.getCategory(relation.getCategory()));
                }

                relationsToCategories.clear();
            }

            return movie;
        }
    }

    /**
     * A query mapper to map movie to requests.
     *
     * @author Baptiste Wicht
     */
    private static final class MovieQueryMapper implements QueryMapper {
        @Override
        public Query constructInsertQuery(Entity entity){
            return new Query(
					"INSERT INTO " + TABLE + " (TITLE, NOTE, FILE, RESOLUTION, DURATION, IMAGE, THE_COLLECTION_FK) VALUES(?,?,?,?,?,?,?)",
					fillArray((Movie) entity, false));
        }

        @Override
        public Query constructUpdateQuery(Entity entity){
            return new Query(
					"UPDATE " + TABLE + " SET TITLE = ?, NOTE = ?, FILE = ?, RESOLUTION = ?, DURATION = ?, IMAGE = ?, THE_COLLECTION_FK = ? WHERE ID = ?",
					fillArray((Movie) entity, true));
        }

		/**
		 * Fill the array with the informations of the movie.
		 *
		 * @param movie The movie to use to fill the array.
		 * @param id Indicate if we must add the id to the array.
		 *
		 * @return The filled array.
		 */
		private static Object[] fillArray(Movie movie, boolean id){
			Object[] values = new Object[7 + (id ? 1 : 0)];

			values[0] = movie.getTitle();
			values[1] = movie.getNote() == null ? DaoNotes.getInstance().getDefaultNote().getValue().intValue() : movie.getNote().getValue().intValue();
			values[2] = movie.getFile();
			values[3] = movie.getResolution() == null ? "" : movie.getResolution().toString();
			values[4] = movie.getDuration() == null ? 0 : movie.getDuration().getTime();
			values[5] = movie.getImage();
			values[6] = movie.getTheCollection().getId();

			if (id){
				values[7] = movie.getId();
			}

			return values;
		}
    }
}