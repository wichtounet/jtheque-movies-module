package org.jtheque.movies.views.impl.controllers.states;

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
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.primary.able.controller.ControllerState;
import org.jtheque.primary.able.controller.FormBean;
import org.jtheque.primary.able.od.Data;
import org.jtheque.primary.utils.controller.AbstractControllerState;
import org.jtheque.primary.utils.edits.GenericDataCreatedEdit;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.undo.able.IUndoRedoService;

import javax.annotation.Resource;

/**
 * A state of movie view correspond with a creation.
 *
 * @author Baptiste Wicht
 */
public final class CreateMovieState extends AbstractControllerState {
    @Resource
    private IMoviesService moviesService;

    @Resource
    private IUndoRedoService undoRedoService;

    @Resource
    private IErrorService errorService;

    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IMovieView movieView;

    @Override
    public void apply() {
        movieView.getModel().setCurrentMovie(moviesService.getEmptyMovie());
        movieView.setDisplayedView(IMovieView.EDIT_VIEW);
    }

    @Override
    public ControllerState save(FormBean bean) {
        if (!movieView.validateContent()) {
            return null;
        }

        IMovieFormBean infos = (IMovieFormBean) bean;

        Movie movie = moviesService.getEmptyMovie();

        infos.fillMovie(movie);

        if (moviesService.fileExists(movie.getFile())) {
            errorService.addError(Errors.newI18nError("movie.errors.existingfile"));

            return null;
        }

        moviesService.create(movie);

        undoRedoService.addEdit(new GenericDataCreatedEdit<Movie>(moviesService, movie));

        movieView.resort();

        return getController().getViewState();
    }

    @Override
    public ControllerState cancel() {
        ControllerState nextState = null;

        movieView.selectFirst();

        if (moviesService.getMovies().size() <= 0) {
            nextState = getController().getViewState();
        }

        return nextState;
    }

    @Override
    public ControllerState view(Data data) {
        switchMovie(data);

        return getController().getViewState();
    }

    /**
     * Switch the current movie.
     *
     * @param data The new movie to display.
     */
    private void switchMovie(Data data) {
        Movie movie = (Movie) data;

        if (uiUtils.askI18nUserForConfirmation("movie.dialogs.confirmSave", "movie.dialogs.confirmSave.title")) {
            getController().save();
        }

        movieView.getModel().setCurrentMovie(movie);
    }
}