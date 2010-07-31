package org.jtheque.movies.views.impl.controllers;

import org.jtheque.errors.able.Errors;
import org.jtheque.errors.able.IErrorService;
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

public class CategoryController extends AbstractController<ICategoryView> {
    @Resource
    private ICategoriesService categoriesService;

    @Resource
    private IUndoRedoService undoRedoService;

    @Resource
    private IErrorService errorService;

    public CategoryController() {
        super(ICategoryView.class);
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("category.actions.ok", "validate");
        translations.put("category.actions.cancel", "close");
        translations.put("category.actions.new", "newCategory");

        return translations;
    }

    private void close() {
        getView().closeDown();
    }

    private void validate() {
        if (getView().validateContent()) {
            String title = getView().getCategoryName();

            if (categoriesService.existsInOtherCategory(title, getCurrentCategory())) {
                errorService.addError(Errors.newI18nError("category.errors.exists"));
            } else {
                getCurrentCategory().setTitle(title);
                getCurrentCategory().setParent(getView().getSelectedCategory());

                if (getView().getModel().getState() == ViewMode.NEW) {
                    categoriesService.create(getCurrentCategory());

                    undoRedoService.addEdit(new GenericDataCreatedEdit<Category>(categoriesService,
                            getView().getModel().getCategory()));
                } else {
                    categoriesService.save(getCurrentCategory());
                }

                close();
            }
        }
    }

    private void newCategory() {
        getView().getModel().setState(ViewMode.NEW);

        getView().getModel().setCategory(categoriesService.getEmptyCategory());
        getView().reload();

        getView().display();
    }

    private void editCategory(Category category) {
        getView().getModel().setState(ViewMode.EDIT);
        getView().getModel().setCategory(category);
        getView().reload();
    }

    /**
     * Return the current category.
     *
     * @return The current category.
     */
    private Category getCurrentCategory() {
        return getView().getModel().getCategory();
    }
}