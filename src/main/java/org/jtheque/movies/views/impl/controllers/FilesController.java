package org.jtheque.movies.views.impl.controllers;

import org.jtheque.movies.views.able.IFilesView;
import org.jtheque.ui.able.Action;
import org.jtheque.ui.utils.AbstractController;

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

public class FilesController extends AbstractController<IFilesView> {
    public FilesController() {
        super(IFilesView.class);
    }

    @Action("files.actions.close")
    private void close() {
        getView().closeDown();
    }

    @Action("iles.actions.refresh")

    private void refresh() {
        getView().refreshData();
    }

    @Action("movie.files")
    public void displayFiles() {
        refresh();
        getView().display();
    }
}