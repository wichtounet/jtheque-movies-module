package org.jtheque.movies.views.impl.controllers;

import org.jtheque.movies.views.able.IFilesView;
import org.jtheque.ui.utils.AbstractController;

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

public class FilesController extends AbstractController<IFilesView> {
    public FilesController() {
        super(IFilesView.class);
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("files.actions.refresh", "refresh");
        translations.put("files.actions.close", "close");
        translations.put("movie.files", "displayFiles");

        return translations;
    }

    private void close() {
        getView().closeDown();
    }

    private void refresh() {
        getView().refreshData();
    }

    public void displayFiles() {
        refresh();
        getView().display();
    }
}