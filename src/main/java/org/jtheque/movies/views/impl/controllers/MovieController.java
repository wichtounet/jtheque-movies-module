package org.jtheque.movies.views.impl.controllers;

import org.jtheque.errors.able.IErrorService;
import org.jtheque.errors.utils.Errors;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.views.able.IAddFromFileView;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.panel.EditMoviePanel;
import org.jtheque.movies.views.impl.panel.players.VLCPlayer;
import org.jtheque.movies.views.impl.panel.players.ViewerPanel;
import org.jtheque.movies.views.impl.panel.players.WMPPlayer;
import org.jtheque.primary.utils.controller.AbstractControllerState;
import org.jtheque.primary.utils.controller.PrincipalController;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.utils.DesktopUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.views.able.IViews;

import javax.annotation.Resource;

import java.io.File;
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

public class MovieController extends PrincipalController {
    @Resource
    private ILanguageService languageService;

    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IMovieView movieView;

    @Resource
    private EditMoviePanel editMoviePanel;

    @Resource
    private IFFMpegService ffMpegService;

    @Resource
    private IErrorService errorService;

    @Resource
    private IAddFromFileView addFromFileView;

    @Resource
    private IMoviesModule moviesModule;

    @Resource
    private IViews iViews;

    private ViewerPanel currentViewer;

    /**
     * Create a new MovieController.
     *
     * @param viewState      The view state.
     * @param modifyState    The modify state.
     * @param newObjectState The new object state.
     */
    public MovieController(AbstractControllerState viewState, AbstractControllerState modifyState,
                           AbstractControllerState newObjectState) {
        super(viewState, modifyState, newObjectState, null);

        viewState.setController(this);
        modifyState.setController(this);
        newObjectState.setController(this);
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("movie.actions.save", "save");
        translations.put("movie.actions.cancel", "cancel");
        translations.put("movie.actions.add", "create");
        translations.put("movie.actions.delete", "manualEdit");
        translations.put("movie.actions.edit", "edit");
        translations.put("movie.actions.infos", "infos");
        translations.put("movie.actions.view", "play");
        translations.put("movie.actions.view.quit", "quitViewer");
        translations.put("movie.auto.actions.add", "autoAdd");
        translations.put("collapse", "collapse");
        translations.put("refresh", "refresh");
        translations.put("expand", "expand");

        return translations;
    }

    @Override
    public void save(){
        save(movieView.fillMovieFormBean());
    }

    private void play(){
        if (movieView.getModel().getCurrentMovie() != null) {
            IMovieConfiguration.Opening opening = moviesModule.getConfig().getOpeningSystem();

            String file = movieView.getModel().getCurrentMovie().getFile();

            switch (opening) {
                case SYSTEM:
                    DesktopUtils.open(new File(file));

                    break;
                case VLC:
                    displayViewer(IMovieView.VLC_VIEW, new File(file));

                    break;
                case WMP:
                    displayViewer(IMovieView.WMP_VIEW, new File(file));

                    break;
            }
        }
    }

    /**
     * Display the specified viewer and load the specified file.
     *
     * @param view The viewer to display.
     * @param file The file to open.
     */
    private void displayViewer(String view, File file) {
        if (view.equals(IMovieView.WMP_VIEW)) {
            setCurrentViewer(file, new ViewerPanel(new WMPPlayer(), this));
        } else if (view.equals(IMovieView.VLC_VIEW)) {
            setCurrentViewer(file, new ViewerPanel(new VLCPlayer(), this));
        }
    }

    /**
     * Set the current viewer.
     *
     * @param file   The file to open in the viewer.
     * @param viewer The viewer to display.
     */
    private void setCurrentViewer(File file, ViewerPanel viewer) {
        iViews.getMainView().setGlassPane(viewer);
        viewer.setFile(file);
        viewer.setVisible(true);
        currentViewer = viewer;
    }

    private void infos() {
        String filePath = editMoviePanel.getFilePath();

        File file = new File(filePath);

        if (StringUtils.isNotEmpty(filePath) && file.exists()) {
            editMoviePanel.setResolution(ffMpegService.getResolution(file));
            editMoviePanel.setDuration(ffMpegService.getDuration(file));
        } else {
            errorService.addError(Errors.newI18nError("movie.errors.filenotfound"));
        }
    }

    private void delete(){
        final boolean yes = uiUtils.getDelegate().askUserForConfirmation(
                languageService.getMessage("movie.dialogs.confirmDelete", movieView.getModel().getCurrentMovie().getDisplayableText()),
                languageService.getMessage("movie.dialogs.confirmDelete.title"));

        if (yes) {
            deleteCurrent();
        }
    }

    private void collapse(){
        movieView.collapseAll();
    }

    private void expand() {
        movieView.expandAll();
    }

    private void refresh() {
        movieView.collapseAll();
    }

    private void quitViewer() {
        if (currentViewer != null) {
            currentViewer.stop();
            currentViewer.setVisible(false);
            iViews.getMainView().setGlassPane(null);
            currentViewer = null;
        }
    }

    private void autoAdd(){
        if(isEditing()){

        } else {
            addFromFileView.display();
        }
    }

    private boolean isEditing() {
        return getState() == getNewObjectState() || getState() == getModifyState();
    }
}