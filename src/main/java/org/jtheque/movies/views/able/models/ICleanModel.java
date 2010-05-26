package org.jtheque.movies.views.able.models;

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
import org.jtheque.ui.able.IModel;

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
