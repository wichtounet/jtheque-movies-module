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

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.primary.able.views.model.IPrincipalDataModel;

/**
 * @author Baptiste Wicht
 */
public interface IMoviesModel extends IPrincipalDataModel<Movie> {
    /**
     * Return the current movie.
     *
     * @return The current movie.
     */
    Movie getCurrentMovie();

    /**
     * Set the current movie.
     *
     * @param currentMovie The new current movie
     */
    void setCurrentMovie(Movie currentMovie);
}
