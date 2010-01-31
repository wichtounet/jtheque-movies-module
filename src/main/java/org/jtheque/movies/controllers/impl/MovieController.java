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

import org.jtheque.core.utils.CoreUtils;
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
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.impl.PrincipalController;
import org.jtheque.utils.DesktopUtils;

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

    @Override
    public void displayViewer(String view, File file) {
        if (view.equals(IMovieView.WMP_VIEW)) {
            setCurrentViewer(file, new ViewerPanel(new WMPPlayer()));
        } else if (view.equals(IMovieView.VLC_VIEW)) {
            setCurrentViewer(file, new ViewerPanel(new VLCPlayer()));
        }
    }

    /**
     * Set the current viewer.
     *
     * @param file   The file to open in the viewer.
     * @param viewer The viewer to display.
     */
    private void setCurrentViewer(File file, ViewerPanel viewer) {
        CoreUtils.getMainView().setGlassPane(viewer);
        viewer.setFile(file);
        viewer.setVisible(true);
        currentViewer = viewer;
    }

    @Override
    public void closeViewer() {
        if (currentViewer != null) {
            currentViewer.stop();
            currentViewer.setVisible(false);
            CoreUtils.getMainView().setGlassPane(null);
            currentViewer = null;
        }
    }

    @Override
    public String getDataType() {
        return IMoviesService.DATA_TYPE;
    }


}