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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.models.able.IMoviesModel;
import org.jtheque.movies.views.impl.panel.JPanelVLC;
import org.jtheque.movies.views.impl.panel.JPanelWMP;
import org.jtheque.movies.views.impl.panel.ViewerPanel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.impl.PrincipalController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.Collection;

/**
 * The Swing controller for the film view. This principal controller has no auto add state.
 *
 * @author Baptiste Wicht
 */
public final class MovieController extends PrincipalController<Movie> implements IMovieController {
    @Resource
    private IMovieView movieView;

    private ViewerPanel current;

    private Action quitPlayerAction;

    @Override
    public IMovieView getView(){
        return movieView;
    }

    /**
     * Init the controller.
     */
    @PostConstruct
    private void init(){
        setState(getViewState());

        quitPlayerAction = Managers.getManager(IResourceManager.class).getAction("quitMovieViewAction");
    }

    @Override
    public void valueChanged(TreeSelectionEvent event){
        TreePath current = ((JTree) event.getSource()).getSelectionPath();

        if (current != null && current.getLastPathComponent() instanceof Movie){
            Movie movie = (Movie) current.getLastPathComponent();

            if (movie != null){
                view(movie);

                Managers.getCore().getLifeCycleManager().setCurrentFunction(movie.getTitle());
            }
        }
    }

    @Override
    public void save(){
        ControllerState newState = getState().save(movieView.fillMovieFormBean());

        if (newState != null){
            setAndApplyState(newState);
        }
    }

    @Override
    public IMoviesModel getViewModel(){
        return (IMoviesModel) movieView.getModel();
    }

    @Override
    public boolean isEditing(){
        return getState() == getNewObjectState() || getState() == getModifyState();
    }

    @Override
    public void displayViewer(String view, File file){
        if (view.equals(IMovieView.WMP_VIEW)){
            setCurrentViewer(file, new JPanelWMP(quitPlayerAction));
        } else if (view.equals(IMovieView.VLC_VIEW)){
            setCurrentViewer(file, new JPanelVLC(quitPlayerAction));
        }
    }

    /**
     * Set the current viewer.
     *
     * @param file   The file to open in the viewer.
     * @param viewer The viewer to display.
     */
    private void setCurrentViewer(File file, ViewerPanel viewer){
        Managers.getManager(IViewManager.class).getViews().getMainView().setGlassPane(viewer);
        viewer.setFile(file);
        viewer.setVisible(true);
        current = viewer;
    }

    @Override
    public void closeViewer(){
        if (current != null){
            current.stop();
            current.setVisible(false);
            Managers.getManager(IViewManager.class).getViews().getMainView().setGlassPane(null);
            current = null;
        }
    }

    @Override
    public void view(Movie movie){
        ControllerState newState = getState().view(movie);

        if (newState != null){
            setAndApplyState(newState);
        }
    }

    @Override
    public void manualEdit(){
        ControllerState newState = getState().manualEdit();

        if (newState != null){
            setAndApplyState(newState);
        }
    }

    @Override
    public void createMovie(){
        ControllerState newState = getState().create();

        if (newState != null){
            setAndApplyState(newState);
        }
    }

    @Override
    public void deleteCurrentMovie(){
        ControllerState newState = getState().delete();

        if (newState != null){
            setAndApplyState(newState);
        }
    }

    @Override
    public void cancel(){
        ControllerState newState = getState().cancel();

        if (newState != null){
            setAndApplyState(newState);
        }
    }

    @Override
    public String getDataType(){
        return IMoviesService.DATA_TYPE;
    }

    @Override
    public Collection<Movie> getDisplayList(){
        return getViewModel().getDisplayList();
    }
}