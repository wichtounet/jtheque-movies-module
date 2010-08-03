package org.jtheque.movies.views.impl.controllers;

import org.jtheque.errors.able.ErrorService;
import org.jtheque.errors.able.Errors;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IFilesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IAddFromFileView;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.ui.able.Controller;
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

public class AddFromFileController extends AbstractController<IAddFromFileView> {
    @Resource
    private IMoviesService moviesService;

    @Resource
    private Controller<IMovieView> movieController;

    @Resource
    private IFilesService filesService;

    @Resource
    private ErrorService errorService;

    public AddFromFileController() {
        super(IAddFromFileView.class);
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("movie.auto.actions.add", "validate");
        translations.put("movie.auto.actions.cancel", "close");

        return translations;
    }

    private void close() {
        getView().closeDown();
    }

    private void validate() {
        if (getView().validateContent()) {
            String filePath = getView().getFilePath();

            if (moviesService.fileExists(filePath)) {
                errorService.addError(Errors.newI18nError("movie.errors.existingfile"));
            } else {
                Movie movie = filesService.createMovie(filePath, getView().getSelectedParsers());

                movieController.getView().select(movie);

                close();
            }
        }
    }
}