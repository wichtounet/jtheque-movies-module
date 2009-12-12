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

import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.ICleanMovieView;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Action to open a movie.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateCleanViewAction extends JThequeAction {
    private static final long serialVersionUID = 1412326778227550519L;
    
    @Resource
    private ICleanMovieView cleanMovieView;
    
    @Resource
    private IMoviesService moviesService;
    
    @Resource
    private IMovieController movieController;
    
    /**
     * Construct a new AcPrintFilm.
     */
    public AcValidateCleanViewAction() {
        super("movie.actions.clean.validate");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moviesService.clean(cleanMovieView.getMovies(), cleanMovieView.getSelectedCleaners());
        cleanMovieView.closeDown();
        movieController.view(CollectionUtils.first(cleanMovieView.getMovies()));
    }
}