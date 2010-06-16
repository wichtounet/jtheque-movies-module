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
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.utils.ui.edt.SimpleTask;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;

/**
 * Action to search film titles from a folder.
 *
 * @author Baptiste Wicht
 */
public final class SearchFilesAction extends JThequeAction {
    private final IFilesService filesService;
    private final IImportFolderView importFolderView;

    /**
     * Create a new AcSearchTitles action.
     *
     * @param filesService The files service.
     * @param importFolderView The import folder view. 
     */
    public SearchFilesAction(IFilesService filesService, IImportFolderView importFolderView) {
        super("generic.view.actions.search");

        this.filesService = filesService;
        this.importFolderView = importFolderView;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (importFolderView.validateContent(IImportFolderView.Phase.CHOOSE_FOLDER)) {
            new Thread(new SearchTitlesRunnable()).start();
        }
    }

    /**
     * A runnable for search titles.
     *
     * @author Baptiste Wicht
     */
    private final class SearchTitlesRunnable implements Runnable {
        @Override
        public void run() {
            SwingUtils.execute(new SimpleTask() {
                @Override
                public void run() {
                    importFolderView.startWait();
                }
            });

            final Collection<File> files = filesService.getMovieFiles(new File(importFolderView.getFolderPath()));

            SwingUtils.execute(new SimpleTask() {
                @Override
                public void run() {
                    importFolderView.setFiles(files);
                    importFolderView.stopWait();
                }
            });
        }
    }
}