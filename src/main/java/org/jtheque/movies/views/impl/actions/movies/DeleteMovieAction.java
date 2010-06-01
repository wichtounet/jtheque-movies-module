package org.jtheque.movies.views.impl.actions.movies;

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

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * Action to delete a film.
 *
 * @author Baptiste Wicht
 */
public final class DeleteMovieAction extends JThequeAction {
    private final ILanguageService languageService;
    private final IMovieController movieController;
    private final IUIUtils uiUtils;

    /**
     * Construct a new DeleteMovieAction.
     *
     * @param languageService The language service.
     * @param uiUtils         The ui utils.
     * @param movieController The movie controller.
     */
    public DeleteMovieAction(ILanguageService languageService, IUIUtils uiUtils, IMovieController movieController) {
        super("movie.actions.delete");

        this.languageService = languageService;
        this.uiUtils = uiUtils;
        this.movieController = movieController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final boolean yes = uiUtils.getDelegate().askUserForConfirmation(
                languageService.getMessage("movie.dialogs.confirmDelete", movieController.getViewModel().getCurrentMovie().getDisplayableText()),
                languageService.getMessage("movie.dialogs.confirmDelete.title"));

        if (yes) {
            movieController.deleteCurrent();
        }
    }
}