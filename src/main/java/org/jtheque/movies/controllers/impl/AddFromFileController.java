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

import org.jtheque.errors.able.IErrorService;
import org.jtheque.errors.utils.Errors;
import org.jtheque.movies.controllers.able.IAddFromFileController;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IFilesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.views.able.IAddFromFileView;
import org.jtheque.spring.utils.SwingSpringProxy;
import org.jtheque.views.utils.AbstractController;

import javax.annotation.Resource;

import java.util.Collection;

/**
 * Controller for the clean view.
 *
 * @author Baptiste Wicht
 */
public final class AddFromFileController extends AbstractController implements IAddFromFileController {
    private final SwingSpringProxy<IAddFromFileView> addFromFileView;

    @Resource
    private IMoviesService moviesService;

    @Resource
    private IFilesService filesService;

    @Resource
    private IMovieController movieController;

    /**
     * Create a new AddFromFileController.
     *
     * @param addFromFileView A proxy to the add from file view.
     */
    public AddFromFileController(SwingSpringProxy<IAddFromFileView> addFromFileView) {
        super();

        this.addFromFileView = addFromFileView;
    }

    @Override
    public IAddFromFileView getView() {
        return addFromFileView.get();
    }

    @Override
    public void add(String filePath, Collection<FileParser> parsers) {
        if (moviesService.fileExists(filePath)) {
            getService(IErrorService.class).addError(Errors.newI18nError("movie.errors.existingfile"));
        } else {
            Movie movie = filesService.createMovie(filePath, parsers);

            movieController.view(movie);

            closeView();
        }
    }
}