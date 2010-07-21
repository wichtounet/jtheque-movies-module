package org.jtheque.movies.views.impl.controllers;

import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IGenerateInfosView;
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

public class GenerateInfosController extends AbstractController<IGenerateInfosView> {
    @Resource
    private IMoviesService moviesService;

    public GenerateInfosController() {
        super(IGenerateInfosView.class);
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("movie.actions.generate.validate", "generate");
        translations.put("movie.auto.actions.cancel", "close");
        translations.put("movie.generate.infos", "display");


        return translations;
    }

    private void close() {
        getView().closeDown();
    }

    private void display() {
        getView().display();
    }

    private void generate() {
        IGenerateInfosView view = getView();

        moviesService.fillInformations(
                moviesService.getMovies(view.getSelectedCategory(), view.areSubCategoriesIncluded()),
                view.mustGenerateDuration(), view.mustGenerateResolution(), view.mustGenerateImage());

        close();
    }
}