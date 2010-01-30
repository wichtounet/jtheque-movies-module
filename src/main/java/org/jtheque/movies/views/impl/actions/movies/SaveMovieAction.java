package org.jtheque.movies.views.impl.actions.movies;

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
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.controllers.able.IMovieController;

import java.awt.event.ActionEvent;

/**
 * Action to save the current film.
 *
 * @author Baptiste Wicht
 */
public final class SaveMovieAction extends JThequeAction {
    /**
     * Construct a new AcSaveFilm.
     */
    public SaveMovieAction(){
        super("movie.actions.save");
    }

    @Override
    public void actionPerformed(ActionEvent e){
        CoreUtils.<IMovieController>getBean("movieController").save();
    }
}