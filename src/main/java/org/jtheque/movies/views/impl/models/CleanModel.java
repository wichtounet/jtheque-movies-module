package org.jtheque.movies.views.impl.models;

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.able.models.ICleanModel;

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
 * A clean model implementation. This model store the current category or movie of the
 * clean view.
 *
 * @author Baptiste Wicht
 */
public final class CleanModel implements ICleanModel {
    private Category category;
    private Movie movie;

    @Override
    public void setCategory(Category category) {
        this.category = category;
        movie = null;
    }

    @Override
    public void setMovie(Movie movie) {
        this.movie = movie;
        category = null;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public Movie getMovie() {
        return movie;
    }

    @Override
    public boolean isMovieMode() {
        return movie != null;
    }
}