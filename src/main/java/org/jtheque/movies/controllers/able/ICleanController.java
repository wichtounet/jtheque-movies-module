package org.jtheque.movies.controllers.able;

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.able.ICleanView;
import org.jtheque.views.able.Controller;

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

/**
 * A clean controller specification.
 *
 * @author Baptiste Wicht
 */
public interface ICleanController extends Controller {
    @Override
    ICleanView getView();

    /**
     * Clean the specified movie.
     *
     * @param movie The movie to clean.
     */
    void clean(Movie movie);

    /**
     * Clean the category.
     *
     * @param category The category to clean.
     */
    void clean(Category category);

    /**
     * Clean the current content.
     */
    void clean();
}