package org.jtheque.movies.services.impl;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A service for movies implementation.
 *
 * @author Baptiste Wicht
 */
public final class MoviesService implements IMoviesService {
    @Resource
    private IDaoMovies daoMovies;

    @Override
    public Movie getEmptyMovie(){
        Movie movie = daoMovies.createMovie();

        movie.setTitle(Managers.getManager(ILanguageManager.class).getMessage("values.new"));

        return movie;
    }

    @Override
    public Collection<Movie> getMovies(){
        return daoMovies.getMovies();
    }

    @Override
    @Transactional
    public boolean delete(Movie movie){
        return daoMovies.delete(movie);
    }

    @Override
    @Transactional
    public void save(Movie movie){
        daoMovies.save(movie);
    }

    @Override
    @Transactional
    public void create(Movie movie){
        daoMovies.create(movie);
    }

    @Override
    public void clean(Collection<Movie> movies, Collection<NameCleaner> cleaners){
        for (Movie movie : movies){
            String title = movie.getTitle();

            for (NameCleaner cleaner : cleaners){
                title = cleaner.clearName(movie, title);
            }

            title = title.trim();

            if (!title.equals(movie.getTitle())){
                movie.setTitle(title);
                save(movie);
            }
        }
    }

    @Override
    public Collection<Movie> getMoviesOf(Category category){
        Collection<Movie> movies = new ArrayList<Movie>(10);

        for (Movie movie : getMovies()){
            if (movie.isOfCategory(category)){
                movies.add(movie);
            }
        }

        return movies;
    }

    @Override
    public Collection<Movie> getDatas(){
        return getMovies();
    }

    @Override
    public void addDataListener(DataListener listener){
        daoMovies.addDataListener(listener);
    }

    @Override
    public String getDataType(){
        return DATA_TYPE;
    }

    @Override
    @Transactional
    public void clearAll(){
        daoMovies.clearAll();
    }
}