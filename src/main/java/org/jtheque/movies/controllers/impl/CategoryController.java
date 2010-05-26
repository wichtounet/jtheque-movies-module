package org.jtheque.movies.controllers.impl;

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

import org.jtheque.errors.able.IErrorService;
import org.jtheque.movies.controllers.able.ICategoryController;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.primary.utils.edits.GenericDataCreatedEdit;
import org.jtheque.primary.able.views.ViewMode;
import org.jtheque.undo.able.IUndoRedoService;
import org.jtheque.views.utils.AbstractController;

import javax.annotation.Resource;

/**
 * Controller for the category view.
 *
 * @author Baptiste Wicht
 */
public final class CategoryController extends AbstractController implements ICategoryController {
    @Resource
    private ICategoriesService categoriesService;

    @Resource
    private ICategoryView categoryView;

    @Override
    public void newCategory() {
        categoryView.getModel().setState(ViewMode.NEW);

        categoryView.getModel().setCategory(categoriesService.getEmptyCategory());
        categoryView.reload();

        displayView();
    }

    @Override
    public void editCategory(Category category) {
        categoryView.getModel().setState(ViewMode.EDIT);
        categoryView.getModel().setCategory(category);
        categoryView.reload();
    }

    @Override
    public void save(String title, Category parent) {
        if (categoriesService.existsInOtherCategory(title, getCurrentCategory())) {
            getService(IErrorService.class).addInternationalizedError("category.errors.exists");
        } else {
            getCurrentCategory().setTitle(title);
            getCurrentCategory().setParent(parent);

            if (categoryView.getModel().getState() == ViewMode.NEW) {
                categoriesService.create(getCurrentCategory());

                getService(IUndoRedoService.class).addEdit(new GenericDataCreatedEdit<Category>(categoriesService, categoryView.getModel().getCategory()));
            } else {
                categoriesService.save(getCurrentCategory());
            }

            closeView();
        }
    }

    /**
     * Return the current category.
     *
     * @return The current category. 
     */
    private Category getCurrentCategory() {
        return categoryView.getModel().getCategory();
    }

    @Override
    public ICategoryView getView() {
        return categoryView;
    }
}