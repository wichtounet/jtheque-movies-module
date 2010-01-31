package org.jtheque.movies.views.impl.actions.movies.image;

import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.controllers.able.IImageController;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IImageView;

import java.awt.event.ActionEvent;

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

/**
 * An action to validate the current image of the image view.
 *
 * @author Baptiste Wicht
 */
public final class ValidateImageViewAction extends JThequeAction {
    /**
     * Construct a new ValidateImageViewAction.
     */
    public ValidateImageViewAction() {
        super("movie.image.actions.validate");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Movie movie = CoreUtils.<IMovieController>getBean("movieController").getViewModel().getCurrentMovie();
        IImageView view = CoreUtils.<IImageController>getBean("imageController").getView();

        CoreUtils.<IMoviesService>getBean("moviesService").saveImage(movie, view.getImage());

        view.closeDown();
    }
}
