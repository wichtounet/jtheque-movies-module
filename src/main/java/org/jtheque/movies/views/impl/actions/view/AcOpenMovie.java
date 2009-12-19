package org.jtheque.movies.views.impl.actions.view;

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
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.MoviesModule;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.utils.DesktopUtils;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Action to open a movie.
 *
 * @author Baptiste Wicht
 */
public final class AcOpenMovie extends JThequeAction {
    private static final long serialVersionUID = 1412326778227550519L;

    @Resource
    private IMovieController movieController;

    @Resource
    private IMoviesModule moviesModule;

    /**
     * Construct a new AcOpenMovie.
     */
    public AcOpenMovie(){
        super("movie.actions.view");

        setIcon(Managers.getManager(IResourceManager.class).getIcon(MoviesModule.IMAGES_BASE_NAME, "play", ImageType.PNG));
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (movieController.getViewModel().getCurrentMovie() != null){
            IMovieConfiguration.Opening opening = moviesModule.getConfig().getOpeningSystem();

            String file = movieController.getViewModel().getCurrentMovie().getFile();

            switch (opening){
                case SYSTEM:
                    DesktopUtils.open(new File(file));

                    break;
                case VLC:
                    movieController.displayViewer(IMovieView.VLC_VIEW, new File(file));

                    break;
                case WMP:
                    movieController.displayViewer(IMovieView.WMP_VIEW, new File(file));

                    break;
            }
        }
    }
}