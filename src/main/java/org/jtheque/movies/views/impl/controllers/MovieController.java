package org.jtheque.movies.views.impl.controllers;

import org.jtheque.errors.ErrorService;
import org.jtheque.errors.Errors;
import org.jtheque.i18n.LanguageService;
import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.views.able.IAddFromFileView;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.panel.players.VLCPlayer;
import org.jtheque.movies.views.impl.panel.players.ViewerPanel;
import org.jtheque.movies.views.impl.panel.players.WMPPlayer;
import org.jtheque.primary.utils.controller.PrincipalController;
import org.jtheque.ui.Action;
import org.jtheque.ui.UIUtils;
import org.jtheque.ui.Controller;
import org.jtheque.utils.DesktopUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.views.Views;

import javax.annotation.Resource;

import java.io.File;

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

public class MovieController extends PrincipalController<Movie, IMovieView> {
    @Resource
    private LanguageService languageService;

    @Resource
    private UIUtils uiUtils;

    @Resource
    private IFFMpegService ffMpegService;

    @Resource
    private ErrorService errorService;

    @Resource
    private IMoviesModule moviesModule;

    @Resource
    private Views views;

    @Resource
    private Controller<IAddFromFileView> addFromFileController;

    private ViewerPanel currentViewer;

    public MovieController() {
        super(IMovieView.class);
    }

    @Override
    public void handleAction(String actionName) {
        if ("movie.actions.cancel".equals(actionName)) {
            cancel();
        } else if ("movie.actions.add".equals(actionName)) {
            create();
        } else if ("movie.actions.delete".equals(actionName)) {
            delete();
        } else if ("movie.actions.edit".equals(actionName)) {
            manualEdit();
        } else {
            super.handleAction(actionName);
        }
    }

    @Override
    @Action("movie.actions.save")
    public void save() {
        save(getView().fillMovieFormBean());
    }

    @Action("movie.actions.view")
    private void play() {
        if (getView().getModel().getCurrentMovie() != null) {
            IMovieConfiguration.Opening opening = moviesModule.getConfig().getOpeningSystem();

            String file = getView().getModel().getCurrentMovie().getFile();

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
        views.getMainView().setGlassPane(viewer);
        viewer.setFile(file);
        viewer.setVisible(true);
        currentViewer = viewer;
    }

    @Action("movie.actions.infos")
    private void infos() {
        String filePath = getView().getEditMoviePanel().getFilePath();

        File file = new File(filePath);

        if (StringUtils.isNotEmpty(filePath) && file.exists()) {
            getView().getEditMoviePanel().setResolution(ffMpegService.getResolution(file));
            getView().getEditMoviePanel().setDuration(ffMpegService.getDuration(file));
        } else {
            errorService.addError(Errors.newI18nError("movie.errors.filenotfound"));
        }
    }

    @Action("movie.actions.delete")
    private void delete() {
        final boolean yes = uiUtils.getDelegate().askUserForConfirmation(
                languageService.getMessage("movie.dialogs.confirmDelete", getView().getModel().getCurrentMovie().getDisplayableText()),
                languageService.getMessage("movie.dialogs.confirmDelete.title"));

        if (yes) {
            deleteCurrent();
        }
    }

    @Action("collapse")
    private void collapse() {
        getView().collapseAll();
    }

    @Action("expand")
    private void expand() {
        getView().expandAll();
    }

    @Action("refresh")
    private void refresh() {
        getView().collapseAll();
    }

    @Action("movie.actions.view.quit")
    private void quitViewer() {
        if (currentViewer != null) {
            currentViewer.stop();
            currentViewer.setVisible(false);
            views.getMainView().setGlassPane(null);
            currentViewer = null;
        }
    }

    @Action("movie.auto.actions.add")
    private void autoAdd() {
        if (isEditing()) {

        } else {
            addFromFileController.getView().display();
        }
    }

    private boolean isEditing() {
        return getState() == getNewObjectState() || getState() == getModifyState();
    }
}