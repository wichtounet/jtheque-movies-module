package org.jtheque.movies.views.able;

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

import org.jtheque.core.managers.view.able.IView;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.panel.MoviePanel;

/**
 * @author Baptiste Wicht
 */
public interface IMovieView extends IView {
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
}
