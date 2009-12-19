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
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.primary.controller.able.IChoiceController;

import java.awt.event.ActionEvent;

/**
 * Action to edit a category.
 *
 * @author Baptiste Wicht
 */
public final class AcEditCategory extends JThequeAction {
    /**
     * Construct a new AcEditKind.
     */
    public AcEditCategory(){
        super("category.actions.edit");
    }

    @Override
    public void actionPerformed(ActionEvent e){
        IChoiceController choiceController = Managers.getManager(IBeansManager.class).getBean("choiceController");

        choiceController.setAction("edit");
        choiceController.setContent(ICategoriesService.DATA_TYPE);
        choiceController.displayView();
    }
}