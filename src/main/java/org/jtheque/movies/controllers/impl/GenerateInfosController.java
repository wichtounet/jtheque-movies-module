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
import org.jtheque.movies.controllers.able.IGenerateInfosController;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IGenerateInfosView;

import javax.annotation.Resource;

/**
 * Controller for the clean view.
 *
 * @author Baptiste Wicht
 */
public final class GenerateInfosController extends AbstractController implements IGenerateInfosController {
    @Resource
    private IGenerateInfosView generateInfosView;

    @Resource
    private IMoviesService moviesService;

    @Override
    public IGenerateInfosView getView(){
        return generateInfosView;
    }

    @Override
    public void generate() {
        moviesService.fillInformations(
                moviesService.getMovies(generateInfosView.getSelectedCategory(), generateInfosView.areSubCategoriesIncluded()),
                generateInfosView.mustGenerateDuration(), generateInfosView.mustGenerateResolution(),
                generateInfosView.mustGenerateImage());

        closeView();
    }
}