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
 * A list model for actors.
 *
 * @author Baptiste Wicht
 */
public final class CategoriesListModel extends SimpleListModel<Category> implements DataListener {
    private final ICategoriesService categoriesService;

    private SimpleListModel<Category> linkedModel;

    /**
     * Construct a new ActorsListModel.
     *
     * @param categoriesService
     */
    public CategoriesListModel(ICategoriesService categoriesService) {
        super();

	    this.categoriesService = categoriesService;

	    this.categoriesService.addDataListener(this);

        setElements(this.categoriesService.getCategories());
    }

    @Override
    public void dataChanged() {
        reload();
    }

    /**
     * Reload the model.
     */
    public void reload() {
        setElements(categoriesService.getCategories());

        if (linkedModel != null) {
            for (Category category : linkedModel.getObjects()) {
                removeElement(category);
            }
        }
    }

    /**
     * Set the linked model of this model. It seems that, when we must reload the data, we remove from this model the data of
     * the linked model.
     *
     * @param linkedModel The model to link to.
     */
    public void setLinkedModel(SimpleListModel<Category> linkedModel) {
        this.linkedModel = linkedModel;
    }
}