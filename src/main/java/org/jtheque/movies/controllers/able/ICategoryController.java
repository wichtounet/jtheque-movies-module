package org.jtheque.movies.controllers.able;

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
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.views.able.Controller;

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

    @Override
    ICategoryView getView();
}