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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.movies.controllers.able.IAddFromFileController;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IFilesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.views.able.IAddFromFileView;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Controller for the clean view.
 *
 * @author Baptiste Wicht
 */
public final class AddFromFileController extends AbstractController implements IAddFromFileController {
    @Resource
    private IAddFromFileView addFromFileView;

    @Resource
    private IMoviesService moviesService;

    @Resource
    private IFilesService filesService;

    @Resource
    private IMovieController movieController;

    @Override
    public IAddFromFileView getView() {
        return addFromFileView;
    }

    @Override
    public void add(String filePath, Collection<FileParser> parsers) {
        if (moviesService.fileExists(filePath)) {
            Managers.getManager(IViewManager.class).displayError(new InternationalizedError("movie.errors.existingfile"));
        } else {
            Movie movie = filesService.createMovie(filePath, parsers);

            movieController.view(movie);

            closeView();
        }
    }
}