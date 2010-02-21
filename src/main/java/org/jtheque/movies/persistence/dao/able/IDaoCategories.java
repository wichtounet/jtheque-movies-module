package org.jtheque.movies.persistence.dao.able;

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

import org.jtheque.core.managers.persistence.able.Dao;
import org.jtheque.movies.persistence.od.able.Category;

import java.util.Collection;

/**
 * A DAO for categories specification.
 *
 * @author Baptiste Wicht
 */
public interface IDaoCategories extends Dao<Category> {
    String TABLE = "T_MOVIE_CATEGORIES";

    /**
     * Return all the categories of the current collection.
     *
     * @return All the categories of the current collection.
     */
    Collection<Category> getCategories();

    /**
     * Return the category with the specified id.
     *
     * @param id The id to search for.
     * @return The category with this ID else null.
     */
    Category getCategory(int id);

    /**
     * Return the category of the specified name.
     *
     * @param name The name of the category.
     * @return the category of the specified name if it exists else null.
     */
    Category getCategory(String name);
}