package org.jtheque.movies.controllers.impl.states;

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

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.able.models.IMoviesModel;
import org.jtheque.primary.able.controller.ControllerState;
import org.jtheque.primary.able.od.Data;
import org.jtheque.primary.utils.edits.GenericDataDeletedEdit;
import org.jtheque.undo.able.IUndoRedoService;

import javax.annotation.Resource;

/**
 * A state of movie view correspond with a visualization.
 *
 * @author Baptiste Wicht
 */
public final class ViewMovieState extends MovieState {
    @Resource
    private IMoviesService moviesService;

    @Resource
    private IUndoRedoService undoRedoService;

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    private IMoviesModel getViewModel() {
        return getController().getViewModel();
    }

    @Override
    public void apply() {
        getController().getView().setDisplayedView(IMovieView.VIEW_VIEW);

        Movie current = getController().getView().getSelectedMovie();

        if (current != getViewModel().getCurrentMovie()) {
            getController().getView().select(getViewModel().getCurrentMovie());
        }
    }

    @Override
    public ControllerState create() {
        return getController().getNewObjectState();
    }

    @Override
    public ControllerState manualEdit() {
        if (getViewModel().getCurrentMovie() == null) {
            return null;
        }

        return getController().getModifyState();
    }

    @Override
    public ControllerState delete() {
        boolean deleted = moviesService.delete(getViewModel().getCurrentMovie());

        if (deleted) {
            undoRedoService.addEdit(new GenericDataDeletedEdit<Movie>(moviesService, getViewModel().getCurrentMovie()));

            getController().getView().selectFirst();
        }

        return null;
    }

    @Override
    public ControllerState view(Data data) {
        Movie movie = (Movie) data;

        getViewModel().setCurrentMovie(movie);

        return null;
    }
}