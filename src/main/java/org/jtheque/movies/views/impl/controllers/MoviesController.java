package org.jtheque.movies.views.impl.controllers;

import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.ui.utils.AbstractController;

import javax.annotation.Resource;

import java.io.File;
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

public class MoviesController extends AbstractController {
    @Resource
    private IMovieView movieView;

    @Resource
    private IMoviesService moviesService;

    @Resource
    private IMoviesModule moviesModule;

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("movie.actions.clean.thumbnails", "cleanThumbnails");

        return translations;
    }

    private void cleanThumbnails() {
        File folder = new File(moviesModule.getThumbnailFolderPath());

        for (File f : folder.listFiles()) {
            String name = f.getName();

            if (moviesService.thumbnailIsNotUsed(name)) {
                boolean delete = f.delete();

                if (!delete) {
                    f.deleteOnExit();
                }
            }
        }
    }
}