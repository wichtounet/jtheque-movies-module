package org.jtheque.movies.services.able;

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.persistence.able.DataContainer;
import org.jtheque.primary.able.services.DataService;

import java.util.Collection;

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
 * A categories service specification.
 *
 * @author Baptiste Wicht
 */
public interface ICategoriesService extends DataContainer<Category>, DataService<Category> {
    String DATA_TYPE = "Categories";

    /**
     * Return the categories of the current collection.
     *
     * @return A List containing the categories of the current collection.
     */
    Collection<Category> getCategories();

    /**
     * Test if a category exists or not.
     *
     * @param category The category to test.
     *
     * @return true if the category exists else false.
     */
    boolean exists(String category);

    /**
     * Return the category of the specified name.
     *
     * @param name The name of the category.
     *
     * @return The category.
     */
    Category getCategory(String name);

    /**
     * Return an empty category.
     *
     * @return An empty category.
     */
    Category getEmptyCategory();

    /**
     * Test if the specified title exists in the other categories.
     *
     * @param title    The title to search for.
     * @param category The category to exclude from search.
     *
     * @return true if this title exists in other category else false.
     */
    boolean existsInOtherCategory(String title, Category category);

    /**
     * Return all the sub categories of the specified category.
     *
     * @param category The categories to get the child for.
     *
     * @return A Collection containing all the sub categories of the specified category.
     */
    Collection<Category> getSubCategories(Category category);
}