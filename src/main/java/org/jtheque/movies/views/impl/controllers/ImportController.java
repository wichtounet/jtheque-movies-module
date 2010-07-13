package org.jtheque.movies.views.impl.controllers;

import org.jtheque.movies.services.able.IFilesService;
import org.jtheque.movies.views.able.IImportFolderView;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.utils.ui.SimpleSwingWorker;

import javax.annotation.Resource;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

public class ImportController extends AbstractController {
    @Resource
    private IImportFolderView importFolderView;

    @Resource
    private IFilesService filesService;

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("generic.view.actions.search", "search");
        translations.put("movie.auto.folder.actions.add", "display");
        translations.put("movie.auto.folder.actions.cancel", "close");
        translations.put("generic.view.actions.delete", "delete");
        translations.put("import.view.actions.import", "importFiles");

        return translations;
    }

    private void search(){
        if (importFolderView.validateContent(IImportFolderView.Phase.CHOOSE_FOLDER)) {
            new SearchTitlesWorker().start();
        }
    }

    private void delete() {
        importFolderView.removeSelectedFile();
    }

    private void close() {
        importFolderView.closeDown();
    }

    private void display() {
        importFolderView.display();
    }

    private void importFiles() {
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
        protected void done() {
            importFolderView.getWindowState().stopWait();
            importFolderView.closeDown();
        }

        @Override
        protected void doWork() {
            filesService.importMovies(importFolderView.getFiles(), importFolderView.getSelectedParsers());
        }
    }

    /**
     * A runnable for search titles.
     *
     * @author Baptiste Wicht
     */
    private final class SearchTitlesWorker extends SimpleSwingWorker {
        private Collection<File> files;

        @Override
        protected void before() {
            importFolderView.getWindowState().startWait();
        }

        @Override
        protected void done() {
            importFolderView.setFiles(files);
            importFolderView.getWindowState().stopWait();
        }

        @Override
        protected void doWork() {
            files = filesService.getMovieFiles(new File(importFolderView.getFolderPath()));
        }
    }
}