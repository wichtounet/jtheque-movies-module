package org.jtheque.movies.controllers.impl.states;

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
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.edits.delete.DeletedMovieEdit;
import org.jtheque.movies.views.impl.models.able.IMoviesModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.od.able.Data;

import javax.annotation.Resource;

/**
 * A state of film view correspond with a visualization.
 *
 * @author Baptiste Wicht
 */
public final class ViewMovieState extends AbstractMovieState {
    @Resource
    private IMovieController controller;

    @Resource
    private IMoviesService moviesService;

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    private IMoviesModel getViewModel(){
        return controller.getViewModel();
    }

    @Override
    public void apply(){
        controller.getView().setDisplayedView(IMovieView.VIEW_VIEW);
    }

    @Override
    public ControllerState create(){
        return controller.getNewObjectState();
    }

    @Override
    public ControllerState manualEdit(){
        if (getViewModel().getCurrentMovie() == null){
            return null;
        }

        return controller.getModifyState();
    }

    @Override
    public ControllerState delete(){
        boolean deleted = moviesService.delete(getViewModel().getCurrentMovie());

        if (deleted){
            Managers.getManager(IUndoRedoManager.class).addEdit(new DeletedMovieEdit(getViewModel().getCurrentMovie()));

            controller.getView().selectFirst();
        }

        return null;
    }

    @Override
    public ControllerState autoEdit(Data data){
        Movie movie = (Movie) data;

        getViewModel().setCurrentMovie(movie);

        return controller.getAutoAddState();
    }

    @Override
    public ControllerState view(Data data){
        Movie movie = (Movie) data;

        getViewModel().setCurrentMovie(movie);

        return null;
    }
}