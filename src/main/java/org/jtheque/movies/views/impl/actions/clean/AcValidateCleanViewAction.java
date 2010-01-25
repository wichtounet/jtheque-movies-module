package org.jtheque.movies.views.impl.actions.clean;

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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.ICleanMovieView;
import org.jtheque.utils.collections.CollectionUtils;

import java.awt.event.ActionEvent;

/**
 * Action to open a movie.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateCleanViewAction extends JThequeAction {
    /**
     * Construct a new AcPrintFilm.
     */
    public AcValidateCleanViewAction(){
        super("movie.actions.clean.validate");

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
		ICleanMovieView cleanMovieView = CoreUtils.getBean("cleanMovieView");

        CoreUtils.<IMoviesService>getBean("moviesService").clean(cleanMovieView.getMovies(), cleanMovieView.getSelectedCleaners());
        cleanMovieView.closeDown();
        CoreUtils.<IMovieController>getBean("movieController").view(CollectionUtils.first(cleanMovieView.getMovies()));
    }
}