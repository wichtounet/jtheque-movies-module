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

import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.models.able.IMoviesModel;
import org.jtheque.primary.controller.able.ControllerState;

import javax.swing.event.TreeSelectionListener;
import java.io.File;

/**
 * @author Baptiste Wicht
 */
public interface IMovieController extends Controller, TreeSelectionListener {
    /**
     * Save the current film.
     */
    void save();

    /**
     * Edit manually a film.
     */
    void manualEdit();

    /**
     * Create a new film.
     */
    void createMovie();

    /**
     * Delete the current film.
     */
    void deleteCurrentMovie();

    /**
     * Cancel the current state.
     */
    void cancel();

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    IMoviesModel getViewModel();

    @Override
    IMovieView getView();

    /**
     * Return the state "view".
     *
     * @return The state "view".
     */
    ControllerState getViewState();

    /**
     * Return the state "auto".
     *
     * @return The state "auto".
     */
    ControllerState getAutoAddState();

    /**
     * Return the state "new".
     *
     * @return The state "new".
     */
    ControllerState getNewObjectState();

    /**
     * Return the state "modify".
     *
     * @return The state "modify".
     */
    ControllerState getModifyState();

    /**
     * Display the movie on the view.
     *
     * @param movie The movie to display.
     */
    void view(Movie movie);

    /**
     * Indicate if we currently edit a movie.
     *
     * @return <code>true</code> if we currently edit a movie else <code>false</code>.
     */
    boolean isEditing();

    /**
     * Display the viewer.
     *
     * @param wmpView The viewer id to display.
     * @param file    The file to play.
     */
    void displayViewer(String wmpView, File file);

    void closeViewer();
}
