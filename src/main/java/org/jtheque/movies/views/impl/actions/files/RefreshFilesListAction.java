package org.jtheque.movies.views.impl.actions.files;

import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.controllers.able.IFilesController;

import java.awt.event.ActionEvent;

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

/**
 * An action to refresh the table of invalid files.
 *
 * @author Baptiste Wicht
 */
public final class RefreshFilesListAction extends JThequeAction {
    /**
     * Construct a new RefreshFilesListAction.
     */
    public RefreshFilesListAction() {
        super("files.actions.refresh");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CoreUtils.<IFilesController>getBean("filesController").refresh();
    }
}
