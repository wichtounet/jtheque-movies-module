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
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.ui.utils.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * Action to validate the kind view.
 *
 * @author Baptiste Wicht
 */
public final class ValidateCategoryViewAction extends JThequeAction {
    private ICategoryController categoryController;

    /**
     * Construct a new AcValidateKindView.
     */
    public ValidateCategoryViewAction() {
        super("category.actions.ok");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ICategoryView view = categoryController.getView();

        if (view.validateContent()) {
            categoryController.save(view.getCategoryName(), view.getSelectedCategory());
        }
    }
}