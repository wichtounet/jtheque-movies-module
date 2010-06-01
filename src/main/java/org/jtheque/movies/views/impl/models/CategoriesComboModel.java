package org.jtheque.movies.views.impl.models;

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
import org.jtheque.persistence.able.DataListener;
import org.jtheque.ui.utils.models.SimpleListModel;

/**
 * A categories combo box model with the data in a cache.
 *
 * @author Baptiste Wicht
 */
public final class CategoriesComboModel extends SimpleListModel<Category> implements DataListener {
    private final Category emptyCategory;

    /**
     * Construct a new CategoriesComboMode.
     *
     * @param categoriesService The categories service.
     */
    public CategoriesComboModel(ICategoriesService categoriesService) {
        super();

        emptyCategory = categoriesService.getEmptyCategory();
        emptyCategory.setTitle(" ");

        addElement(emptyCategory);

        categoriesService.addDataListener(this);
    }

    /**
     * Return the selected data in the model.
     *
     * @return The data who's selected.
     */
    public Category getSelectedCategory() {
        Category selected = getSelectedItem();

        return selected == emptyCategory ? null : selected;
    }

    @Override
    public void setSelectedItem(Object anObject) {
        if (anObject == null) {
            super.setSelectedItem(emptyCategory);
        }

        super.setSelectedItem(anObject);
    }

    @Override
    public void dataChanged() {
        setElements(emptyCategory);

        fireContentsChanged(this, 0, getSize());
	}
}