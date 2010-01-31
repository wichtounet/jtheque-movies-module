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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.controllers.able.IMovieController;

import java.awt.event.ActionEvent;

/**
 * Action to delete a film.
 *
 * @author Baptiste Wicht
 */
public final class DeleteMovieAction extends JThequeAction {
    /**
     * Construct a new DeleteMovieAction.
     */
    public DeleteMovieAction() {
        super("movie.actions.delete");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final boolean yes = Managers.getManager(IViewManager.class).askUserForConfirmation(
                CoreUtils.getMessage("movie.dialogs.confirmDelete",
                        CoreUtils.<IMovieController>getBean("movieController").
                                getViewModel().getCurrentMovie().getDisplayableText()),
                CoreUtils.getMessage("movie.dialogs.confirmDelete.title"));

        if (yes) {
            CoreUtils.<IMovieController>getBean("movieController").deleteCurrent();
        }
    }
}