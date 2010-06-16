package org.jtheque.movies.persistence.dao.able;

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
import org.jtheque.persistence.able.Dao;

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
     *
     * @return The category with this ID else null.
     */
    Category getCategory(int id);

    /**
     * Return the category of the specified name.
     *
     * @param name The name of the category.
     *
     * @return the category of the specified name if it exists else null.
     */
    Category getCategory(String name);

    /**
     * Return the category with the given temporary id.
     *
     * @param id The temporary id of the category.
     * @return The Category with the specified temporary id if there is one otherwise null.
     */
    Category getCategoryByTemporaryId(int id);
}