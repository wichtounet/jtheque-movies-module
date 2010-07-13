package org.jtheque.movies.views.impl.controllers;

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.ICleanView;
import org.jtheque.movies.views.able.IMovieView;
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

public class CleanController extends AbstractController {
    @Resource
    private ICleanView cleanView;

    @Resource
    private IMovieView movieView;

    @Resource
    private IMoviesService moviesService;

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("movie.actions.clean.movie", "cleanMovie");
        translations.put("movie.actions.clean.validate", "clean");
        translations.put("movie.auto.actions.cancel", "close");

        return translations;
    }

    private void close(){
        cleanView.closeDown();
    }

    private void cleanMovie() {
        Movie movie = movieView.getModel().getCurrentMovie();

        if (movie != null) {
            cleanView.getModel().setMovie(movie);
            cleanView.display();
        }
    }

    public void clean() {
        if (cleanView.getModel().isMovieMode()) {
            moviesService.clean(cleanView.getModel().getMovie(), cleanView.getSelectedCleaners());

            movieView.refreshData();
        } else {
            moviesService.clean(
                    moviesService.getMovies(cleanView.getModel().getCategory(), cleanView.areSubCategoriesIncluded()),
                    cleanView.getSelectedCleaners());
        }

        cleanView.closeDown();
    }
}