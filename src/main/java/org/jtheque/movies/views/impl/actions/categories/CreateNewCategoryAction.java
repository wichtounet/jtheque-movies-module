package org.jtheque.movies.views.impl.actions.categories;

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

import org.jtheque.movies.controllers.able.ICategoryController;
import org.jtheque.ui.utils.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * An action to create a new category.
 *
 * @author Baptiste Wicht
 */
public final class CreateNewCategoryAction extends JThequeAction {
    private final ICategoryController categoryController;

    /**
     * Construct a new CreateNewCategoryAction.
     *
     * @param categoryController The category controller.
     */
    public CreateNewCategoryAction(ICategoryController categoryController) {
        super("category.actions.new");

        this.categoryController = categoryController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        categoryController.newCategory();
    }
}