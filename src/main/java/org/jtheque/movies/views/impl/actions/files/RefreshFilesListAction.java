package org.jtheque.movies.views.impl.actions.files;

import org.jtheque.movies.controllers.able.IFilesController;
import org.jtheque.ui.utils.actions.JThequeAction;

import java.awt.event.ActionEvent;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * An action to refresh the table of invalid files.
 *
 * @author Baptiste Wicht
 */
public final class RefreshFilesListAction extends JThequeAction {
    private final IFilesController filesController;

    /**
     * Construct a new RefreshFilesListAction.
     *
     * @param filesController The files controller. 
     */
    public RefreshFilesListAction(IFilesController filesController) {
        super("files.actions.refresh");

        this.filesController = filesController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        filesController.refresh();
    }
}
