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

import org.jtheque.errors.able.Errors;
import org.jtheque.errors.able.ErrorService;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.primary.able.controller.ControllerState;
import org.jtheque.primary.able.controller.FormBean;
import org.jtheque.primary.able.od.Data;
import org.jtheque.primary.utils.controller.AbstractControllerState;
import org.jtheque.ui.able.Controller;
import org.jtheque.ui.able.UIUtils;

import javax.annotation.Resource;

/**
 * A state of movie view correspond with a modify.
 *
 * @author Baptiste Wicht
 */
public final class ModifyMovieState extends AbstractControllerState {
    @Resource
    private IMoviesService moviesService;

    @Resource
    private ErrorService errorService;

    @Resource
    private UIUtils uiUtils;

    @Resource
    private Controller<IMovieView> movieController;

    @Override
    public void apply() {
        getMovieView().setDisplayedView(IMovieView.EDIT_VIEW);
        getMovieView().getCurrentView().setMovie(getMovieView().getModel().getCurrentMovie());

        getMovieView().getModel().getCurrentMovie().saveToMemento();
    }

    @Override
    public ControllerState save(FormBean bean) {
        if (!getMovieView().validateContent()) {
            return null;
        }

        IMovieFormBean infos = (IMovieFormBean) bean;

        Movie movie = getMovieView().getModel().getCurrentMovie();

        infos.fillMovie(movie);

        //If the file has changed and the new file already exists in application
        if (moviesService.fileExistsInOtherMovie(movie, movie.getFile())) {
            errorService.addError(Errors.newI18nError("movie.errors.existingfile"));

            return null;
        }

        moviesService.save(movie);

        getMovieView().refreshData();

        return getController().getViewState();
    }

    @Override
    public ControllerState cancel() {
        getMovieView().getModel().getCurrentMovie().restoreMemento();

        return getController().getViewState();
    }

    @Override
    public ControllerState view(Data data) {
        Movie movie = (Movie) data;

        if (uiUtils.askI18nUserForConfirmation("movie.dialogs.confirmSave", "movie.dialogs.confirmSave.title")) {
            getController().save();
        } else {
            getMovieView().getModel().getCurrentMovie().restoreMemento();
        }

        getMovieView().getModel().setCurrentMovie(movie);

        return getController().getViewState();
    }

    private IMovieView getMovieView(){
        return movieController.getView();
    }
}