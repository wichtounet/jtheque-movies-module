package org.jtheque.movies.controllers.impl;

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

import org.jtheque.movies.controllers.able.IFilesController;
import org.jtheque.movies.views.able.IFilesView;
import org.jtheque.views.utils.AbstractController;

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