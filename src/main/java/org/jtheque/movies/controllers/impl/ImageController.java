package org.jtheque.movies.controllers.impl;

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

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.movies.controllers.able.IImageController;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.views.able.IImageView;

import javax.annotation.Resource;

/**
 * Controller for the category view.
 *
 * @author Baptiste Wicht
 */
public final class ImageController extends AbstractController implements IImageController {
    @Resource
    private IImageView imageView;

    @Resource
    private IMovieController movieController;

    @Override
    public void editImage() {
        imageView.displayMovie(movieController.getViewModel().getCurrentMovie());
        imageView.display();
    }

    @Override
    public IImageView getView() {
        return imageView;
    }
}