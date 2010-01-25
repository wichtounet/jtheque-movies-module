package org.jtheque.movies.views.able.models;

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
import org.jtheque.primary.view.impl.models.able.IPrincipalDataModel;

/**
 * @author Baptiste Wicht
 */
public interface IMoviesModel extends IPrincipalDataModel<Movie> {
    /**
     * Return the current movie.
     *
     * @return The current movie.
     */
    Movie getCurrentMovie();

    /**
     * Set the current movie.
     *
     * @param currentMovie The new current movie
     */
    void setCurrentMovie(Movie currentMovie);
}
