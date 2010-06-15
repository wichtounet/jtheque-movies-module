package org.jtheque.movies.controllers.able;

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
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.able.models.IMoviesModel;
import org.jtheque.primary.able.controller.IPrincipalController;

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
     * Play the current movie using a viewer depending on the current configuration of the user.
     */
    void playCurrentMovie();
}
