package org.jtheque.movies.controllers.able;

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
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.able.models.IMoviesModel;
import org.jtheque.primary.controller.able.IPrincipalController;

import java.io.File;

/**
 * @author Baptiste Wicht
 */
public interface IMovieController extends IPrincipalController<Movie> {
    /**
     * Save the current film.
     */
    void save();

    /**
     * Indicate if we currently edit a movie.
     *
     * @return <code>true</code> if we currently edit a movie else <code>false</code>.
     */
    boolean isEditing();

    /**
     * Close the current viewer.
     */
    void closeViewer();

    @Override
    IMovieView getView();

    @Override
    IMoviesModel getViewModel();

    /**
     * Play the current movie using a viewer depending on the current configuration of
     * the user. 
     */
    void playCurrentMovie();
}
