package org.jtheque.movies.persistence.dao.able;

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

import org.jtheque.core.managers.persistence.able.JThequeDao;
import org.jtheque.movies.persistence.od.able.Movie;

import java.util.Collection;

/**
 * A DAO for movies specification.
 *
 * @author Baptiste Wicht
 */
public interface IDaoMovies extends JThequeDao {
    String TABLE = "T_MOVIES";
    String MOVIES_CATEGORIES_TABLE = "T_MOVIES_CATEGORIES";

    /**
     * Return the movies of the current collection.
     *
     * @return All the movies of the current collection.
     */
    Collection<Movie> getMovies();

    /**
     * Create a new movie.
     *
     * @param movie The movie to create.
     */
    void create(Movie movie);

    /**
     * Create a movie.
     *
     * @return An empty movie.
     */
    Movie createMovie();

    /**
     * Save the movie on the database.
     *
     * @param movie The movie to save.
     */
    void save(Movie movie);

    /**
     * Delete a movie.
     *
     * @param movie The movie to delete.
     * @return true if the object is deleted else false.
     */
    boolean delete(Movie movie);

    /**
     * Return the movie of the specified ID.
     *
     * @param id The searched ID.
     * @return The movie of the specified id if found else null.
     */
    Movie getMovie(int id);

    /**
     * Return the movie of the specified title.
     *
     * @param title The title to search for.
     * @return The movie with the specified title if there is one else null.
     */
    Movie getMovie(String title);
}