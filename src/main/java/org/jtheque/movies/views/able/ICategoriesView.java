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

import org.jtheque.errors.Error;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;

import java.awt.Component;
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
    void validate(Collection<Error> errors);

    /**
     * Return the implementation of the view.
     *
     * @return The implementation of the view.
     */
    Component getImpl();
}
