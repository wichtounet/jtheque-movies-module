package org.jtheque.movies.views.impl.actions.categories;

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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.controllers.able.ICategoryController;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.views.able.ICategoryView;

import java.awt.event.ActionEvent;

/**
 * Action to validate the kind view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateCategoryView extends JThequeAction {
    /**
     * Construct a new AcValidateKindView.
     */
    public AcValidateCategoryView(){
        super("category.actions.ok");
    }

    @Override
    public void actionPerformed(ActionEvent e){
        ICategoryView view = CoreUtils.getBean("categoryView");

        if (view.validateContent()){
			String title = view.getFieldName().getText();

			if(CoreUtils.<ICategoriesService>getBean("categoriesService").exists(title)){
				Managers.getManager(IViewManager.class).displayError(new InternationalizedError("category.errors.exists"));
			} else {
				CoreUtils.<ICategoryController>getBean("categoryController").save(title);

            	view.closeDown();
			}
        }
    }
}