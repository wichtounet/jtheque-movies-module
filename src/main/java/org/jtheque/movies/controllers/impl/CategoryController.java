package org.jtheque.movies.controllers.impl;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.controllers.able.ICategoryController;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.primary.controller.impl.undo.GenericDataCreatedEdit;
import org.jtheque.primary.view.able.ViewMode;

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
        if (CoreUtils.<ICategoriesService>getBean("categoriesService").existsInOtherCategory(title, getCurrentCategory())) {
            Managers.getManager(IViewManager.class).displayError(new InternationalizedError("category.errors.exists"));
        } else {
            getCurrentCategory().setTitle(title);
            getCurrentCategory().setParent(parent);

            if (categoryView.getModel().getState() == ViewMode.NEW) {
                categoriesService.create(getCurrentCategory());

                Managers.getManager(IUndoRedoManager.class).addEdit(
                        new GenericDataCreatedEdit<Category>("categoriesService", categoryView.getModel().getCategory()));
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