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
import org.jtheque.primary.able.views.ViewMode;
import org.jtheque.primary.utils.edits.GenericDataCreatedEdit;
import org.jtheque.spring.utils.SwingSpringProxy;
import org.jtheque.undo.able.IUndoRedoService;
import org.jtheque.views.utils.AbstractController;

import javax.annotation.Resource;

/**
 * Controller for the category view.
 *
 * @author Baptiste Wicht
 */
public final class CategoryController extends AbstractController implements ICategoryController {
    private final SwingSpringProxy<ICategoryView> categoryView;

    @Resource
    private ICategoriesService categoriesService;

    /**
     * Construct a new CategoryController.
     *
     * @param categoryView A proxy to the category view.
     */
    public CategoryController(SwingSpringProxy<ICategoryView> categoryView) {
        super();

        this.categoryView = categoryView;
    }

    @Override
    public void newCategory() {
        categoryView.get().getModel().setState(ViewMode.NEW);

        categoryView.get().getModel().setCategory(categoriesService.getEmptyCategory());
        categoryView.get().reload();

        displayView();
    }

    @Override
    public void editCategory(Category category) {
        categoryView.get().getModel().setState(ViewMode.EDIT);
        categoryView.get().getModel().setCategory(category);
        categoryView.get().reload();
    }

    @Override
    public void save(String title, Category parent) {
        if (categoriesService.existsInOtherCategory(title, getCurrentCategory())) {
            getService(IErrorService.class).addInternationalizedError("category.errors.exists");
        } else {
            getCurrentCategory().setTitle(title);
            getCurrentCategory().setParent(parent);

            if (categoryView.get().getModel().getState() == ViewMode.NEW) {
                categoriesService.create(getCurrentCategory());

                getService(IUndoRedoService.class).addEdit(new GenericDataCreatedEdit<Category>(categoriesService,
                        categoryView.get().getModel().getCategory()));
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
        return categoryView.get().getModel().getCategory();
    }

    @Override
    public ICategoryView getView() {
        return categoryView.get();
    }
}