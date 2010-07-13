package org.jtheque.movies.views.impl.controllers;

import org.jtheque.errors.able.IErrorService;
import org.jtheque.errors.utils.Errors;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.primary.able.views.ViewMode;
import org.jtheque.primary.utils.edits.GenericDataCreatedEdit;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.undo.able.IUndoRedoService;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

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

public class CategoryController extends AbstractController {
    @Resource
    private ICategoryView categoryView;

    @Resource
    private ICategoriesService categoriesService;

    @Resource
    private IUndoRedoService undoRedoService;

    @Resource
    private IErrorService errorService;

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("category.actions.ok", "validate");
        translations.put("category.actions.cancel", "close");
        translations.put("category.actions.new", "newCategory");

        return translations;
    }

    private void close(){
        categoryView.closeDown();
    }

    private void validate() {
        if (categoryView.validateContent()) {
            String title = categoryView.getCategoryName();

            if (categoriesService.existsInOtherCategory(title, getCurrentCategory())) {
                errorService.addError(Errors.newI18nError("category.errors.exists"));
            } else {
                getCurrentCategory().setTitle(title);
                getCurrentCategory().setParent(categoryView.getSelectedCategory());

                if (categoryView.getModel().getState() == ViewMode.NEW) {
                    categoriesService.create(getCurrentCategory());

                    undoRedoService.addEdit(new GenericDataCreatedEdit<Category>(categoriesService,
                            categoryView.getModel().getCategory()));
                } else {
                    categoriesService.save(getCurrentCategory());
                }

                close();
            }
        }
    }

    private void newCategory() {
        categoryView.getModel().setState(ViewMode.NEW);

        categoryView.getModel().setCategory(categoriesService.getEmptyCategory());
        categoryView.reload();

        categoryView.display();
    }

    private void editCategory(Category category) {
        categoryView.getModel().setState(ViewMode.EDIT);
        categoryView.getModel().setCategory(category);
        categoryView.reload();
    }

    /**
     * Return the current category.
     *
     * @return The current category.
     */
    private Category getCurrentCategory() {
        return categoryView.getModel().getCategory();
    }
}