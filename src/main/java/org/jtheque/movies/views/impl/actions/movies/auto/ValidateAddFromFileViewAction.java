package org.jtheque.movies.views.impl.actions.movies.auto;

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

import org.jtheque.movies.controllers.able.IAddFromFileController;
import org.jtheque.movies.views.able.IAddFromFileView;
import org.jtheque.ui.utils.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * An action to add a movie from a file.
 *
 * @author Baptiste Wicht
 */
public final class ValidateAddFromFileViewAction extends JThequeAction {
    private final IAddFromFileController addFromFileController;

    /**
     * Construct a new ValidateAddFromFileViewAction.
     *
     * @param addFromFileController
     */
    public ValidateAddFromFileViewAction(IAddFromFileController addFromFileController) {
        super("movie.auto.actions.add");

        this.addFromFileController = addFromFileController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IAddFromFileView view = addFromFileController.getView();

        if (view.validateContent()) {
            addFromFileController.add(view.getFilePath(), view.getSelectedParsers());
        }
    }
}