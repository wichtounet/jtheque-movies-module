package org.jtheque.movies.views.impl.controllers;

import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IGenerateInfosView;
import org.jtheque.ui.able.Action;
import org.jtheque.ui.utils.AbstractController;

import javax.annotation.Resource;

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

    @Action("movie.auto.actions.cancel")
    private void close() {
        getView().closeDown();
    }

    @Action("movie.generate.infos")
    private void display() {
        getView().display();
    }

    @Action("movie.actions.generate.validate")
    private void generate() {
        IGenerateInfosView view = getView();

        moviesService.fillInformations(
                moviesService.getMovies(view.getSelectedCategory(), view.areSubCategoriesIncluded()),
                view.mustGenerateDuration(), view.mustGenerateResolution(), view.mustGenerateImage());

        close();
    }
}