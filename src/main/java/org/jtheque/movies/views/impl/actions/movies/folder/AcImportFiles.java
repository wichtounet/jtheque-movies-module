package org.jtheque.movies.views.impl.actions.movies.folder;

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
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.movies.views.able.IImportFolderView;
import org.jtheque.movies.services.able.IFilesService;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Action to search film titles from a folder.
 *
 * @author Baptiste Wicht
 */
public final class AcImportFiles extends JThequeAction {
    private static final long serialVersionUID = -8086054102467874235L;

    @Resource
    private IFilesService filesService;

    @Resource
    private IImportFolderView importFolderView;

    /**
     * Create a new AcSearchTitles action.
     */
    public AcImportFiles() {
        super("generic.view.actions.search");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (importFolderView.validateContent(IImportFolderView.Phase.CHOOSE_FILES)) {
            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    importFolderView.startWait();

                    new Thread(new ImportFilesRunnable()).start();
                }
            });
        }
    }

    /**
     * A runnable for search titles.
     *
     * @author Baptiste Wicht
     */
    private final class ImportFilesRunnable implements Runnable {
        @Override
        public void run() {
            filesService.importMovies(importFolderView.getFiles(), importFolderView.getSelectedParsers());

            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    importFolderView.stopWait();
                    importFolderView.closeDown();
                }
            });
        }
    }
}