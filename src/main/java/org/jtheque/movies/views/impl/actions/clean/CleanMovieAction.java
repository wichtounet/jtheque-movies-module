package org.jtheque.movies.views.impl.actions.clean;

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

import org.jtheque.movies.controllers.able.ICleanController;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.ui.utils.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * Action to open a movie.
 *
 * @author Baptiste Wicht
 */
public final class CleanMovieAction extends JThequeAction {
    private final IMovieController movieController;
    private final ICleanController cleanController;

    /**
     * Construct a new AcPrintFilm.
     *
     * @param movieController The movie controller.
     * @param cleanController The clean controller.
     */
    public CleanMovieAction(IMovieController movieController, ICleanController cleanController) {
        super("movie.actions.clean.movie");

        this.movieController = movieController;
        this.cleanController = cleanController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Movie movie = movieController.getViewModel().getCurrentMovie();

        if (movie != null) {
            cleanController.clean(movie);
        }
    }
}