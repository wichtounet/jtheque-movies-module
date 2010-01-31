package org.jtheque.movies.views.able.models;

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

import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;

/**
 * A clean model specification.
 *
 * @author Baptiste Wicht
 */
public interface ICleanModel extends IModel {
    /**
     * Set the category to clean.
     *
     * @param category The category to clean.
     */
    void setCategory(Category category);

    /**
     * Set the movie to clean.
     *
     * @param movie The movie to clean.
     */
    void setMovie(Movie movie);

    /**
     * Return the category to clean.
     *
     * @return The category to clean.
     */
    Category getCategory();

    /**
     * Return the movie to clean.
     *
     * @return The movie to clean.
     */
    Movie getMovie();

    /**
     * Indicate if it's movie mode.
     *
     * @return true if this is movie mode else false is this category mode.
     */
    boolean isMovieMode();
}
