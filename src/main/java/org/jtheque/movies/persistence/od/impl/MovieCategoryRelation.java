package org.jtheque.movies.persistence.od.impl;

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
    public int getCategory(){
        return category;
    }

    /**
     * Set the category of the relation.
     *
     * @param category The category of the relation.
     */
    public void setCategory(int category){
        this.category = category;
    }

    /**
     * Return the movie of the relation.
     *
     * @return The movie of the relation.
     */
    public int getMovie(){
        return movie;
    }

    /**
     * Set the movie of the relation.
     *
     * @param movie The movie of the relation.
     */
    public void setMovie(int movie){
        this.movie = movie;
    }
}