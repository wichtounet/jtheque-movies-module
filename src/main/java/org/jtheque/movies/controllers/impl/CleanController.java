package org.jtheque.movies.controllers.impl;

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
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.ICleanView;
import org.jtheque.movies.views.able.models.ICleanModel;
import org.jtheque.views.utils.AbstractController;

import javax.annotation.Resource;

/**
 * Controller for the clean view.
 *
 * @author Baptiste Wicht
 */
public final class CleanController extends AbstractController implements ICleanController {
    @Resource
    private ICleanView cleanView;

    @Resource
    private IMoviesService moviesService;

    @Resource
    private IMovieController movieController;

    @Override
    public ICleanView getView() {
        return cleanView;
    }

    @Override
    public void clean(Movie movie) {
        getViewModel().setMovie(movie);
        cleanView.display();
    }

    @Override
    public void clean(Category category) {
        getViewModel().setCategory(category);
        cleanView.display();
    }

    @Override
    public void clean() {
        if (getViewModel().isMovieMode()) {
            moviesService.clean(getViewModel().getMovie(), cleanView.getSelectedCleaners());

            movieController.getView().refreshData();
        } else {
            moviesService.clean(
                    moviesService.getMovies(getViewModel().getCategory(), cleanView.areSubCategoriesIncluded()),
                    cleanView.getSelectedCleaners());
        }

        cleanView.closeDown();
    }

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    private ICleanModel getViewModel() {
        return cleanView.getModel();
	}
}