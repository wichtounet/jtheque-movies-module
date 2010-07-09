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

import org.jtheque.movies.services.able.IFilesService;
import org.jtheque.movies.views.able.IImportFolderView;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.ui.SimpleSwingWorker;

import java.awt.event.ActionEvent;

/**
 * Action to search film titles from a folder.
 *
 * @author Baptiste Wicht
 */
public final class ImportFilesAction extends JThequeAction {
    private final IFilesService filesService;
    private final IImportFolderView importFolderView;

    /**
     * Create a new AcSearchTitles action.
     *
     * @param importFolderView The  import folder view.
     * @param filesService     The files service.
     */
    public ImportFilesAction(IImportFolderView importFolderView, IFilesService filesService) {
        super("generic.view.actions.search");
        this.importFolderView = importFolderView;
        this.filesService = filesService;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (importFolderView.validateContent(IImportFolderView.Phase.CHOOSE_FILES)) {
            new ImportFilesWorker().start();
        }
    }

    /**
     * A runnable for import files.
     *
     * @author Baptiste Wicht
     */
    private final class ImportFilesWorker extends SimpleSwingWorker {
        @Override
        protected void before() {
            importFolderView.getWindowState().startWait();
        }

        @Override
        protected void done() {
            importFolderView.getWindowState().stopWait();
            importFolderView.closeDown();
        }

        @Override
        protected void doWork() {
            filesService.importMovies(importFolderView.getFiles(), importFolderView.getSelectedParsers());
        }
    }
}