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
import org.jtheque.movies.views.able.models.ICategoryModel;
import org.jtheque.primary.able.views.ViewMode;

/**
 * A model for the kind view.
 *
 * @author Baptiste Wicht
 */
public final class CategoryModel implements ICategoryModel {
    private ViewMode state = ViewMode.NEW;
    private Category category;

    @Override
    public ViewMode getState() {
        return state;
    }

    @Override
    public void setState(ViewMode state) {
        this.state = state;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }
}