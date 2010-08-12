package org.jtheque.movies.persistence.dao.able;

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

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.persistence.Dao;

import java.util.Collection;

/**
 * A DAO for movies specification.
 *
 * @author Baptiste Wicht
 */
public interface IDaoMovies extends Dao<Movie> {
    String TABLE = "T_MOVIES";
    String MOVIES_CATEGORIES_TABLE = "T_MOVIES_CATEGORIES";

    /**
     * Return the movies of the current collection.
     *
     * @return All the movies of the current collection.
     */
    Collection<Movie> getMovies();

    /**
     * Return the movie of the specified ID.
     *
     * @param id The searched ID.
     *
     * @return The movie of the specified id if found else null.
     */
    Movie getMovie(int id);

    /**
     * Return the movie of the specified title.
     *
     * @param title The title to search for.
     *
     * @return The movie with the specified title if there is one else null.
     */
    Movie getMovie(String title);
}