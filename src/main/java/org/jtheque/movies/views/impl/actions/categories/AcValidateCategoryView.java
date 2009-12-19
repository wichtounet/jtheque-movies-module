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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.movies.controllers.able.ICategoryController;
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
        ICategoryView view = Managers.getManager(IBeansManager.class).getBean("categoryView");

        if (view.validateContent()){
            Managers.getManager(IBeansManager.class).<ICategoryController>getBean("categoryController").save(view.getFieldName().getText());

            view.closeDown();
        }
    }
}