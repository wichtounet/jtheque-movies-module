package org.jtheque.movies.views.impl.fb;

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

import org.jtheque.core.utils.db.Note;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.primary.controller.able.FormBean;

import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public interface IMovieFormBean extends FormBean {
    /**
     * Set the title.
     *
     * @param title The title.
     */
    void setTitle(String title);

    /**
     * Set the categories of the movie.
     *
     * @param categories The categories of the movie.
     */
    void setCategories(Collection<Category> categories);

    /**
     * Set the path to the file.
     *
     * @param file The path to the file.
     */
    void setFile(String file);

    /**
     * Set the note of the movie.
     *
     * @param note The note of the movie.
     */
    void setNote(Note note);

    /**
     * Fill the movie with the infos of the form bean.
     *
     * @param movie The movie to fill.
     */
    void fillMovie(Movie movie);

    /**
     * Set the duration of the form bean.
     *
     * @param duration The duration of the form bean.
     */
    void setDuration(PreciseDuration duration);

    /**
     * Set the resolution of the form bean.
     *
     * @param resolution The resolution of the form bean.
     */
    void setResolution(Resolution resolution);
}
