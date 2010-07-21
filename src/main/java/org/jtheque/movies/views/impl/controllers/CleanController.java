package org.jtheque.movies.views.impl.controllers;

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.ICleanView;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.ui.able.IController;
import org.jtheque.ui.utils.AbstractController;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

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

public class CleanController extends AbstractController<ICleanView> {
    @Resource
    private IController<IMovieView> movieController;

    @Resource
    private IMoviesService moviesService;

    public CleanController() {
        super(ICleanView.class);
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("movie.actions.clean.movie", "cleanMovie");
        translations.put("movie.actions.clean.validate", "clean");
        translations.put("movie.auto.actions.cancel", "close");

        return translations;
    }

    private void close(){
        getView().closeDown();
    }

    private void cleanMovie() {
        Movie movie = movieController.getView().getModel().getCurrentMovie();

        if (movie != null) {
            getView().getModel().setMovie(movie);
            getView().display();
        }
    }

    public void clean() {
        if (getView().getModel().isMovieMode()) {
            moviesService.clean(getView().getModel().getMovie(), getView().getSelectedCleaners());

            movieController.getView().refreshData();
        } else {
            moviesService.clean(
                    moviesService.getMovies(getView().getModel().getCategory(), getView().areSubCategoriesIncluded()),
                    getView().getSelectedCleaners());
        }

        getView().closeDown();
    }
}