package org.jtheque.movies.views.impl.actions.movies.folder;

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

import org.jtheque.movies.views.able.IImportFolderView;
import org.jtheque.ui.utils.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * Action to delete the selected title from the list.
 *
 * @author Baptiste Wicht
 */
public final class DeleteFileAction extends JThequeAction {
    private final IImportFolderView importFolderView;

    /**
     * Create a new AcDeleteTitle action.
     *
     * @param importFolderView The import folder view.
     */
    public DeleteFileAction(IImportFolderView importFolderView) {
        super("generic.view.actions.delete");

        this.importFolderView = importFolderView;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        importFolderView.removeSelectedFile();
    }
}