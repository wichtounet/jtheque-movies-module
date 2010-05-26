package org.jtheque.movies.persistence.od.impl;

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
 * A relation between a category and a movie.
 *
 * @author Baptiste Wicht
 */
public final class MovieCategoryRelation {
    private int category;
    private int movie;

    /**
     * Return the category of the relation.
     *
     * @return The category of the relation.
     */
    public int getCategory() {
        return category;
    }

    /**
     * Set the category of the relation.
     *
     * @param category The category of the relation.
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * Return the movie of the relation.
     *
     * @return The movie of the relation.
     */
    public int getMovie() {
        return movie;
    }

    /**
     * Set the movie of the relation.
     *
     * @param movie The movie of the relation.
     */
    public void setMovie(int movie) {
        this.movie = movie;
    }
}