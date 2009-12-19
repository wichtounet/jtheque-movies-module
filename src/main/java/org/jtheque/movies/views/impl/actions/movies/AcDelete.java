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
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.movies.controllers.able.IMovieController;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Action to delete a film.
 *
 * @author Baptiste Wicht
 */
public final class AcDelete extends JThequeAction {
    private static final long serialVersionUID = 1220800654712365346L;

    @Resource
    private IMovieController movieController;

    /**
     * Construct a new AcDelete.
     */
    public AcDelete(){
        super("movie.actions.delete");
    }

    @Override
    public void actionPerformed(ActionEvent e){
        final boolean yes = Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("movie.dialogs.confirmDelete",
                        movieController.getViewModel().getCurrentMovie().getDisplayableText()),
                Managers.getManager(ILanguageManager.class).getMessage("movie.dialogs.confirmDelete.title"));

        if (yes){
            movieController.deleteCurrentMovie();
        }
    }
}