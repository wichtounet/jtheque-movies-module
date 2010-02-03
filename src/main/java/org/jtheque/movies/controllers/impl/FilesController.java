package org.jtheque.movies.controllers.impl;

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

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.movies.controllers.able.IFilesController;
import org.jtheque.movies.views.able.IFilesView;

import javax.annotation.Resource;

/**
 * Controller for the files view.
 *
 * @author Baptiste Wicht
 */
public final class FilesController extends AbstractController implements IFilesController {
    @Resource
    private IFilesView filesView;

    @Override
    public IFilesView getView() {
        return filesView;
    }

    @Override
    public void refresh() {
        filesView.refreshData();
    }

    @Override
    public void displayFiles() {
        refresh();
        displayView();
    }
}