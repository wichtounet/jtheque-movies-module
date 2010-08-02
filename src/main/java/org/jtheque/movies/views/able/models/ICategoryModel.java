package org.jtheque.movies.views.able.models;

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
import org.jtheque.primary.able.views.ViewMode;
import org.jtheque.ui.able.Model;

/**
 * @author Baptiste Wicht
 */
public interface ICategoryModel extends Model {
    /**
     * Return the state of the model.
     *
     * @return The state of the model.
     */
    ViewMode getState();

    /**
     * Set the state of the model.
     *
     * @param state The state of the model.
     */
    void setState(ViewMode state);

    /**
     * Return the category.
     *
     * @return The category.
     */
    Category getCategory();

    /**
     * Set the current category.
     *
     * @param category The current category.
     */
    void setCategory(Category category);
}
