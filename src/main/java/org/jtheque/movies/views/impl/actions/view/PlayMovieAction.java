package org.jtheque.movies.views.impl.actions.view;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.MoviesModule;
import org.jtheque.movies.controllers.able.IMovieController;

import java.awt.event.ActionEvent;

/**
 * Action to open a movie.
 *
 * @author Baptiste Wicht
 */
public final class PlayMovieAction extends JThequeAction {
    /**
     * Construct a new PlayMovieAction.
     */
    public PlayMovieAction() {
        super("movie.actions.view");

        setIcon(Managers.getManager(IResourceManager.class).getIcon(MoviesModule.IMAGES_BASE_NAME, "play", ImageType.PNG));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IMovieController movieController = CoreUtils.getBean("movieController");

        if (movieController.getViewModel().getCurrentMovie() != null) {
            movieController.playCurrentMovie();
        }
    }
}