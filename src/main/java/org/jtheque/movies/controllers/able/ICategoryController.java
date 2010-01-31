package org.jtheque.movies.controllers.able;

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

import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.movies.persistence.od.able.Category;

/**
 * @author Baptiste Wicht
 */
public interface ICategoryController extends Controller {
    /**
     * Display the view to add a new Borrower.
     */
    void newCategory();

    /**
     * Display the view to edit a kind.
     *
     * @param category The category to edit.
     */
    void editCategory(Category category);

    /**
     * Save modifications made to the kind.
     *
     * @param name   The name of the kind.
     * @param parent The parent category.
     */
    void save(String name, Category parent);
}
