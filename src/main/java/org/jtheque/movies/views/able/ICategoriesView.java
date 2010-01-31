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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;

import java.awt.*;
import java.util.Collection;

/**
 * A categories view specification.
 *
 * @author Baptiste Wicht
 */
public interface ICategoriesView {
    /**
     * Fill the movie form bean
     *
     * @param fb The movie to fill.
     */
    void fillFilm(IMovieFormBean fb);

    /**
     * Reload the view with an another movie.
     *
     * @param movie The new current movie.
     */
    void reload(Movie movie);

    /**
     * Validate the panel.
     *
     * @param errors The errors to fill.
     */
    void validate(Collection<JThequeError> errors);

    /**
     * Return the implementation of the view.
     *
     * @return The implementation of the view.
     */
    Component getImpl();
}
