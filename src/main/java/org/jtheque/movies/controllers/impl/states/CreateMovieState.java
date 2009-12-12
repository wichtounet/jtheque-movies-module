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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.edits.create.CreatedMovieEdit;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.models.able.IMoviesModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Data;

import javax.annotation.Resource;

/**
 * A state of film view correspond with a creation.
 *
 * @author Baptiste Wicht
 */
public final class CreateMovieState extends AbstractMovieState {
    @Resource
    private IMovieController controller;

    @Resource
    private IMoviesService moviesService;

    /**
     * Return the model of the view. 
     * 
     * @return The model of the view. 
     */
    private IMoviesModel getViewModel() {
        return controller.getViewModel();
    }

    @Override
    public void apply() {
        getViewModel().setCurrentMovie(moviesService.getEmptyMovie());
        controller.getView().setDisplayedView(IMovieView.EDIT_VIEW);
    }

    @Override
    public ControllerState save(FormBean bean) {
        IMovieFormBean infos = (IMovieFormBean) bean;

        Movie movie = moviesService.getEmptyMovie();

        infos.fillMovie(movie);

        moviesService.create(movie);

        Managers.getManager(IUndoRedoManager.class).addEdit(new CreatedMovieEdit(movie));

        controller.getView().resort();

        return controller.getViewState();
    }

    @Override
    public ControllerState cancel() {
        ControllerState nextState = null;

        controller.getView().selectFirst();

        if (moviesService.getMovies().size() <= 0) {
            nextState = controller.getViewState();
        }

        return nextState;
    }

    @Override
    public ControllerState autoEdit(Data data) {
        switchMovie(data);

        return controller.getAutoAddState();
    }

    @Override
    public ControllerState view(Data data) {
        switchMovie(data);

        return controller.getViewState();
    }

    /**
     * Switch the current movie.
     *
     * @param data The new movie to display. 
     */
    private void switchMovie(Data data){
        Movie movie = (Movie) data;

        if (Managers.getManager(IViewManager.class).askI18nUserForConfirmation(
                "movie.dialogs.confirmSave",
                "movie.dialogs.confirmSave.title")) {
            controller.save();
        }

        getViewModel().setCurrentMovie(movie);
    }
}