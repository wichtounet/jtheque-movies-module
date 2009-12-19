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
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.movies.controllers.able.ICategoryController;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.movies.views.impl.edits.create.CreatedCategoryEdit;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * Controller for the category view.
 *
 * @author Baptiste Wicht
 */
public final class CategoryController extends AbstractController implements ICategoryController {
    private Category currentCategory;

    @Resource
    private ICategoriesService categoriesService;

    @Resource
    private ICategoryView categoryView;

    @Override
    public void newCategory(){
        categoryView.getModel().setState(ViewMode.NEW);

        categoryView.reload();
        currentCategory = categoriesService.getEmptyCategory();

        displayView();
    }

    @Override
    public void editCategory(Category category){
        categoryView.getModel().setState(ViewMode.EDIT);

        categoryView.reload(category);
        currentCategory = category;
    }

    @Override
    public void save(String name){
        currentCategory.setTitle(name);

        if (categoryView.getModel().getState() == ViewMode.NEW){
            categoriesService.create(currentCategory);

            Managers.getManager(IUndoRedoManager.class).addEdit(new CreatedCategoryEdit(currentCategory));
        } else {
            categoriesService.save(currentCategory);
        }
    }

    /**
     * Return the DataView of the controller.
     *
     * @return The DataView.
     */
    @Override
    public ICategoryView getView(){
        return categoryView;
    }
}