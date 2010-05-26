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

import org.jtheque.movies.controllers.able.IGenerateInfosController;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IGenerateInfosView;
import org.jtheque.views.utils.AbstractController;

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
    public IGenerateInfosView getView() {
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