package org.jtheque.movies.views.impl.fb;

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

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.persistence.able.Note;
import org.jtheque.primary.able.controller.FormBean;

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
