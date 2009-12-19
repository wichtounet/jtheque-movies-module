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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.models.able.IMoviesModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Data;

import javax.annotation.Resource;

/**
 * A state of film view correspond with a modify.
 *
 * @author Baptiste Wicht
 */
public final class ModifyMovieState extends AbstractMovieState {
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
        controller.getView().setDisplayedView(IMovieView.EDIT_VIEW);
        controller.getView().getCurrentView().setMovie(getViewModel().getCurrentMovie());

        getViewModel().getCurrentMovie().saveToMemento();
    }

    @Override
    public ControllerState save(FormBean bean){
        IMovieFormBean infos = (IMovieFormBean) bean;

        Movie movie = getViewModel().getCurrentMovie();

        String oldTitle = movie.getTitle();

        infos.fillMovie(movie);

        moviesService.save(movie);

        if (!oldTitle.equals(movie.getTitle())){
            controller.getView().resort();
            controller.getView().select(movie);
        }

        return controller.getViewState();
    }

    @Override
    public ControllerState cancel(){
        getViewModel().getCurrentMovie().restoreMemento();

        return controller.getViewState();
    }

    @Override
    public ControllerState autoEdit(Data data){
        Managers.getManager(IViewManager.class).displayI18nText("movie.dialogs.currentEdit");

        return null;
    }

    @Override
    public ControllerState view(Data data){
        Movie movie = (Movie) data;

        if (Managers.getManager(IViewManager.class).askI18nUserForConfirmation(
                "movie.dialogs.confirmSave",
                "movie.dialogs.confirmSave.title")){
            controller.save();
        } else {
            getViewModel().getCurrentMovie().restoreMemento();
        }

        getViewModel().setCurrentMovie(movie);

        return controller.getViewState();
    }
}