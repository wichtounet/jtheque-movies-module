package org.jtheque.movies.views.able;

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

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.able.models.IMoviesModel;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.panel.MoviePanel;
import org.jtheque.ui.able.IView;
import org.jtheque.views.able.components.MainComponent;

/**
 * @author Baptiste Wicht
 */
public interface IMovieView extends IView, MainComponent {
    String EDIT_VIEW = "edit_movie";
    String VIEW_VIEW = "view_movie";
    String VLC_VIEW = "vlc_view";
    String WMP_VIEW = "wmp_view";

    /**
     * Fill a <code>FilmFormBean</code> with the infos in the interface.
     *
     * @return The filled <code>FilmFormBean</code>.
     */
    IMovieFormBean fillMovieFormBean();

    /**
     * Set the intern view to display.
     *
     * @param view The view to display.
     */
    void setDisplayedView(String view);

    /**
     * Return the current view.
     *
     * @return The current view to display.
     */
    MoviePanel getCurrentView();

    /**
     * Sort the tree.
     */
    void resort();

    /**
     * Select the first element of the tree.
     */
    void selectFirst();

    /**
     * Expand the tree.
     */
    void expandAll();

    /**
     * Collapse tree.
     */
    void collapseAll();

    /**
     * Select the specified movie in the tree.
     *
     * @param movie The movie to select.
     */
    void select(Movie movie);

    /**
     * Refresh the datas.
     */
    void refreshData();

    /**
     * Return the selected movie.
     *
     * @return The selected movie.
     */
    Movie getSelectedMovie();

    @Override
    IMoviesModel getModel();
}
