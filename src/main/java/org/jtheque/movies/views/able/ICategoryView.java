package org.jtheque.movies.views.able;

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
import org.jtheque.ui.View;

/**
 * A category view specification.
 *
 * @author Baptiste Wicht
 */
public interface ICategoryView extends View {
    /**
     * Return the field containing the name of the kind.
     *
     * @return The field for the name.
     */
    String getCategoryName();

    @Override
    ICategoryModel getModel();

    /**
     * Reload the view.
     */
    void reload();

    /**
     * Return the selected category of the view.
     *
     * @return The selected category of the view.
     */
    Category getSelectedCategory();
}
