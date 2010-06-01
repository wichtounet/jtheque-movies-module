package org.jtheque.movies.persistence.dao.impl;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.collections.able.IDaoCollections;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.CollectionData;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.MovieCategoryRelation;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.persistence.able.Entity;
import org.jtheque.persistence.able.IDaoNotes;
import org.jtheque.persistence.able.IDaoPersistenceContext;
import org.jtheque.persistence.able.QueryMapper;
import org.jtheque.persistence.impl.DaoNotes;
import org.jtheque.persistence.utils.CachedJDBCDao;
import org.jtheque.persistence.utils.Query;
import org.jtheque.primary.able.od.Data;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.CollectionUtils;

import org.springframework.jdbc.core.RowMapper;

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
public final class DaoMovies extends CachedJDBCDao<Movie> implements IDaoMovies {
    private final RowMapper<Movie> rowMapper = new MovieRowMapper();
    private final RowMapper<MovieCategoryRelation> relationRowMapper = new RelationRowMapper();
    private final QueryMapper queryMapper = new MovieQueryMapper();

    private Collection<MovieCategoryRelation> relationsToCategories;

    @Resource
    private IDaoPersistenceContext daoPersistenceContext;

    @Resource
    private IDaoCollections daoCollections;

    @Resource
    private IDaoCategories daoCategories;

    @Resource
    private IDaoNotes daoNotes;

    /**
     * Construct a new DaoMovies.
     */
    public DaoMovies() {
        super(TABLE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Movie> getMovies() {
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
    private Collection<? extends Data> getMovies(org.jtheque.collections.able.Collection collection) {
        if (collection == null || !collection.isSaved()) {
            return getAll();
        }

        load();

        Collection<CollectionData> movies = new ArrayList<CollectionData>(getCache().values());

        CollectionUtils.filter(movies, new CollectionFilter(collection));

        return movies;
    }

    @Override
    public boolean delete(Movie movie) {
        daoPersistenceContext.getTemplate().update("DELETE FROM " + MOVIES_CATEGORIES_TABLE + " WHERE THE_MOVIE_FK = ?", movie.getId());

        return super.delete(movie);
    }

    @Override
    public Movie getMovie(int id) {
        return get(id);
    }

    @Override
    public boolean exists(Movie entity) {
        return getMovie(entity.getTitle()) != null;
    }

    @Override
    public Movie getMovie(String title) {
        for (Movie movie : getMovies()) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }

        return null;
    }

    @Override
    public void save(Movie movie) {
        super.save(movie);

        daoPersistenceContext.getTemplate().update("DELETE FROM " + MOVIES_CATEGORIES_TABLE + " WHERE THE_MOVIE_FK = ?", movie.getId());

        createLinks(movie);
    }

    /**
     * Create the links between the movie and the categories.
     *
     * @param movie The movie.
     */
    private void createLinks(Movie movie) {
        for (Category category : movie.getCategories()) {
            daoPersistenceContext.getTemplate().update("INSERT INTO " + MOVIES_CATEGORIES_TABLE + " (THE_MOVIE_FK, THE_CATEGORY_FK) VALUES(?,?)", movie.getId(), category.getId());
        }
    }

    @Override
    public void create(Movie movie) {
        movie.setTheCollection(daoCollections.getCurrentCollection());

        //First we create the movie in the table
        super.create(movie);

        //next, we can create the links to the categories
        createLinks(movie);
    }

    @Override
    public Movie create() {
        return new MovieImpl();
    }

    @Override
    protected QueryMapper getQueryMapper() {
        return queryMapper;
    }

    @Override
    protected RowMapper<Movie> getRowMapper() {
        return rowMapper;
    }

    @Override
    protected void loadCache() {
        relationsToCategories = daoPersistenceContext.getTemplate().query("SELECT * FROM " + MOVIES_CATEGORIES_TABLE, relationRowMapper);

        Collection<Movie> movies = daoPersistenceContext.getSortedList(TABLE, rowMapper);

        for (Movie movie : movies) {
            getCache().put(movie.getId(), movie);
        }

        setCacheEntirelyLoaded();

        relationsToCategories.clear();
    }

    @Override
    protected void load(int i) {
        Movie book = daoPersistenceContext.getDataByID(TABLE, i, rowMapper);

        getCache().put(i, book);
    }

    /**
     * A row mapper to map a resultset to a relation between category and movie.
     *
     * @author Baptiste Wicht
     */
    private static final class RelationRowMapper implements RowMapper<MovieCategoryRelation> {
        @Override
        public MovieCategoryRelation mapRow(ResultSet rs, int i) throws SQLException {
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
    private final class MovieRowMapper implements RowMapper<Movie> {
        @Override
        public Movie mapRow(ResultSet rs, int i) throws SQLException {
            Movie movie = create();

            movie.setId(rs.getInt("ID"));
            movie.setTitle(rs.getString("TITLE"));
            movie.setFile(rs.getString("FILE"));
            movie.setImage(rs.getString("IMAGE"));
            movie.setTheCollection(daoCollections.getCollection(rs.getInt("THE_COLLECTION_FK")));

            if (StringUtils.isNotEmpty(rs.getString("RESOLUTION"))) {
                movie.setResolution(new Resolution(rs.getString("RESOLUTION")));
            }

            if (StringUtils.isNotEmpty(rs.getString("DURATION"))) {
                movie.setDuration(new PreciseDuration(rs.getLong("DURATION")));
            }

            if (StringUtils.isNotEmpty(rs.getString("NOTE"))) {
                movie.setNote(daoNotes.getNote(DaoNotes.NoteType.getEnum(rs.getInt("NOTE"))));
            } else {
                movie.setNote(daoNotes.getDefaultNote());
            }

            mapRelations(movie);

            return movie;
        }

        /**
         * Map the relations of the movie.
         *
         * @param movie The movie to map the relations for.
         */
        private void mapRelations(Movie movie) {
            if (relationsToCategories != null && !relationsToCategories.isEmpty()) {
                for (MovieCategoryRelation relation : relationsToCategories) {
                    if (relation.getMovie() == movie.getId()) {
                        movie.addCategory(daoCategories.getCategory(relation.getCategory()));
                    }
                }
            } else {
                relationsToCategories = daoPersistenceContext.getTemplate().query("SELECT * FROM " + MOVIES_CATEGORIES_TABLE + " WHERE THE_MOVIE_FK = ?", relationRowMapper, movie.getId());

                for (MovieCategoryRelation relation : relationsToCategories) {
                    movie.addCategory(daoCategories.getCategory(relation.getCategory()));
                }

                relationsToCategories.clear();
            }
        }
    }

    /**
     * A query mapper to map movie to requests.
     *
     * @author Baptiste Wicht
     */
    private final class MovieQueryMapper implements QueryMapper {
        @Override
        public Query constructInsertQuery(Entity entity) {
            return new Query(
                    "INSERT INTO " + TABLE + " (TITLE, NOTE, FILE, RESOLUTION, DURATION, IMAGE, THE_COLLECTION_FK) VALUES(?,?,?,?,?,?,?)",
                    fillArray((Movie) entity, false));
        }

        @Override
        public Query constructUpdateQuery(Entity entity) {
            return new Query(
                    "UPDATE " + TABLE + " SET TITLE = ?, NOTE = ?, FILE = ?, RESOLUTION = ?, DURATION = ?, IMAGE = ?, THE_COLLECTION_FK = ? WHERE ID = ?",
                    fillArray((Movie) entity, true));
        }

        /**
         * Fill the array with the informations of the movie.
         *
         * @param movie The movie to use to fill the array.
         * @param id    Indicate if we must add the id to the array.
         * @return The filled array.
         */
        private Object[] fillArray(Movie movie, boolean id) {
            Object[] values = new Object[7 + (id ? 1 : 0)];

            values[0] = movie.getTitle();
            values[1] = movie.getNote() == null ? daoNotes.getDefaultNote().getValue().intValue() : movie.getNote().getValue().intValue();
            values[2] = movie.getFile();
            values[3] = movie.getResolution() == null ? "" : movie.getResolution().toString();
            values[4] = movie.getDuration() == null ? 0 : movie.getDuration().getTime();
            values[5] = movie.getImage();
            values[6] = movie.getTheCollection().getId();

            if (id) {
                values[7] = movie.getId();
            }

            return values;
        }
    }

}