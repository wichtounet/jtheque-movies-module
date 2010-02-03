package org.jtheque.movies.views.impl.panel;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;

import javax.swing.JPanel;
import java.util.Collection;

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

/**
 * A Movie panel. It's a panel for a specific mode for movies.
 *
 * @author Baptiste Wicht
 */
public abstract class MoviePanel extends JPanel {
    private final String key;

    /**
     * Construct a new MoviePanel.
     *
     * @param key The key identifying the panel.
     */
    MoviePanel(String key) {
        super();

        this.key = key;
    }

    /**
     * Set the current movie.
     *
     * @param movie The current movie.
     */
    public abstract void setMovie(Movie movie);

    /**
     * Validate the view.
     *
     * @param errors The errors list to fill.
     */
    public abstract void validate(Collection<JThequeError> errors);

    /**
     * Fill a movie form bean with the informations of the view.
     *
     * @return the filled movie form bean.
     */
    public abstract IMovieFormBean fillMovieFormBean();

    /**
     * Return the key of the panel.
     *
     * @return The key of the panel.
     */
    public final String getKey() {
        return key;
    }
}