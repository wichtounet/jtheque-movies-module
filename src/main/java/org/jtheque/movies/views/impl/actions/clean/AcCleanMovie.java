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
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.able.ICleanMovieView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Action to open a movie.
 *
 * @author Baptiste Wicht
 */
public final class AcCleanMovie extends JThequeAction {
    private static final long serialVersionUID = 1412326778227550519L;

    @Resource
    private IMovieController movieController;

    @Resource
    private ICleanMovieView cleanMovieView;
    
    /**
     * Construct a new AcPrintFilm.
     */
    public AcCleanMovie() {
        super("movie.actions.clean.movie");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Movie movie = movieController.getViewModel().getCurrentMovie();
        
        if(movie != null){
            cleanMovieView.clean(movie);
        }
    }
}