package org.jtheque.movies.views.impl.choiceActions;

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

import org.jtheque.movies.controllers.able.ICategoryController;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.primary.view.impl.choice.AbstractChoiceAction;
import org.jtheque.primary.view.impl.choice.ModifyChoiceActionUtils;

import javax.annotation.Resource;

/**
 * An action to modify the selected item.
 *
 * @author Baptiste Wicht
 */
public final class ModifyChoiceAction extends AbstractChoiceAction {
    @Resource
    private ICategoryController categoryController;

    @Override
    public boolean canDoAction(String action){
        return "edit".equals(action);
    }

    @Override
    public void execute(){
        if(ModifyChoiceActionUtils.execute(getSelectedItem(), getContent())){
            return;
        }

        if (ICategoriesService.DATA_TYPE.equals(getContent())){
            categoryController.editCategory((Category) getSelectedItem());
            categoryController.displayView();
        }
    }
}