package org.jtheque.movies.views.impl.models;

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.able.models.ICleanModel;

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