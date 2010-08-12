package org.jtheque.movies.views.impl.choice;

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

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.views.able.ICleanView;
import org.jtheque.primary.utils.choice.AbstractChoiceAction;
import org.jtheque.ui.Controller;

import javax.annotation.Resource;

/**
 * An action to modify the selected item.
 *
 * @author Baptiste Wicht
 */
public final class CleanChoiceAction extends AbstractChoiceAction {
    @Resource
    private Controller<ICleanView> cleanController;

    @Override
    public boolean canDoAction(String action) {
        return "clean".equals(action);
    }

    @Override
    public void execute() {
        if (ICategoriesService.DATA_TYPE.equals(getContent())) {
            cleanController.getView().getModel().setCategory((Category) getSelectedItem());
            cleanController.getView().display();
        }
    }
}