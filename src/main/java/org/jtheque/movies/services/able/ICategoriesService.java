package org.jtheque.movies.services.able;

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.primary.services.able.DataService;

import java.util.Collection;

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
     * @return true if the category exists else false.
     */
    boolean exists(String category);

    /**
     * Return the category of the specified name.
     *
     * @param name The name of the category.
     * @return The category.
     */
    Category getCategory(String name);

    /**
     * Return an empty category.
     *
     * @return An empty category.
     */
    Category getEmptyCategory();

    boolean existsInOtherCategory(String title, Category category);

	Collection<Category> getSubCategories(Category category);
}