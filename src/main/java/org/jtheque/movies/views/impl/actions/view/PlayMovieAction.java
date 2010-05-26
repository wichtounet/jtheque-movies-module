package org.jtheque.movies.views.impl.actions.view;

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

import org.jtheque.movies.MoviesResources;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.resources.able.IResourceService;
import org.jtheque.ui.utils.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * Action to open a movie.
 *
 * @author Baptiste Wicht
 */
public final class PlayMovieAction extends JThequeAction {
	private final IMovieController movieController;

    /**
     * Construct a new PlayMovieAction.
     *
     * @param movieController
     */
    public PlayMovieAction(IMovieController movieController, IResourceService resourceService) {
        super("movie.actions.view");

	    this.movieController = movieController;

        setIcon(resourceService.getIcon(MoviesResources.PLAY_ICON));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (movieController.getViewModel().getCurrentMovie() != null) {
            movieController.playCurrentMovie();
        }
    }
}