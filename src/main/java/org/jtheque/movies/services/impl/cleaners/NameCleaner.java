package org.jtheque.movies.services.impl.cleaners;

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

import org.jtheque.movies.persistence.od.able.Movie;

/**
 * An object to clean the name of a movie.
 *
 * @author Baptiste Wicht
 */
public interface NameCleaner {
    /**
     * Return the internationalized title of the cleaner.
     *
     * @return The internationalized title of the cleaner.
     */
    String getTitle();

    /**
     * Clear the name.
     *
     * @param name  The name to clear.
     * @param movie The movie to clean the name for.
     * @return the cleared name.
     */
    String clearName(Movie movie, String name);
}