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

import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.able.models.IMoviesModel;
import org.jtheque.movies.views.impl.panel.players.VLCPlayer;
import org.jtheque.movies.views.impl.panel.players.ViewerPanel;
import org.jtheque.movies.views.impl.panel.players.WMPPlayer;
import org.jtheque.primary.able.controller.ControllerState;
import org.jtheque.primary.utils.controller.PrincipalController;
import org.jtheque.utils.DesktopUtils;
import org.jtheque.views.able.IViews;

import javax.annotation.Resource;
import java.io.File;

/**
 * The Swing controller for the film view. This principal controller has no auto add state.
 *
 * @author Baptiste Wicht
 */
public final class MovieController extends PrincipalController<Movie> implements IMovieController {
    @Resource
    private IMovieView movieView;

    @Resource
    private IMoviesModule moviesModule;

    private ViewerPanel currentViewer;

    /**
     * Create a new MovieController.
     *
     * @param viewState      The view state.
     * @param modifyState    The modify state.
     * @param newObjectState The new object state.
     * @param autoAddState   The auto add state.
     */
    public MovieController(ControllerState viewState, ControllerState modifyState, ControllerState newObjectState,
                           ControllerState autoAddState) {
        super(viewState, modifyState, newObjectState, autoAddState);
    }

    @Override
    public IMovieView getView() {
        return movieView;
    }

    @Override
    public void save() {
        save(movieView.fillMovieFormBean());
    }

    @Override
    public IMoviesModel getViewModel() {
        return (IMoviesModel) movieView.getModel();
    }

    @Override
    public void playCurrentMovie() {
        IMovieConfiguration.Opening opening = moviesModule.getConfig().getOpeningSystem();

        String file = getViewModel().getCurrentMovie().getFile();

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

    @Override
    public boolean isEditing() {
        return getState() == getNewObjectState() || getState() == getModifyState();
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
        getService(IViews.class).getMainView().setGlassPane(viewer);
        viewer.setFile(file);
        viewer.setVisible(true);
        currentViewer = viewer;
    }

    @Override
    public void closeViewer() {
        if (currentViewer != null) {
            currentViewer.stop();
            currentViewer.setVisible(false);
            getService(IViews.class).getMainView().setGlassPane(null);
            currentViewer = null;
        }
    }

    @Override
    public String getDataType() {
        return IMoviesService.DATA_TYPE;
    }


}