package org.jtheque.movies.services.able;

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

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;

import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public interface IMoviesService extends DataContainer<Movie> {
    String DATA_TYPE = "Movies";
    String SORT_BY_CATEGORY = "SORT_CATEGORY";

    /**
     * Create an empty film.
     *
     * @return An empty film.
     */
    Movie getEmptyMovie();

    /**
     * Return the movies of the current collection.
     *
     * @return A List containing all the movies of the current collection.
     */
    Collection<Movie> getMovies();

    /**
     * Delete the movie.
     *
     * @param movie The movie to delete.
     * @return true if the movie has been deleted else false.
     */
    boolean delete(Movie movie);

    /**
     * Save the movie.
     *
     * @param movie The movie to save.
     */
    void save(Movie movie);

    /**
     * Create the movie.
     *
     * @param movie The movie to create.
     */
    void create(Movie movie);

    /**
     * Clean all the movies using the specified cleaners.
     *
     * @param movies   The movies to clean.
     * @param cleaners The cleaners to use to clean the movies.
     */
    void clean(Collection<Movie> movies, Collection<NameCleaner> cleaners);

    /**
     * Return all the movies of the specified category.
     *
     * @param category The searched category.
     * @return A Collection containing all the movies of the specified category.
     */
    Collection<Movie> getMoviesOf(Category category);
}
