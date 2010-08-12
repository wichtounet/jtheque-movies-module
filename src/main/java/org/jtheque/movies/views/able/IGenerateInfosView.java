package org.jtheque.movies.views.able;

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.ui.View;

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

/**
 * A view specification to clean the name of a movie.
 *
 * @author Baptiste Wicht
 */
public interface IGenerateInfosView extends View {
    /**
     * Indicate if the user want to generate the duration.
     *
     * @return true if the user want to generate the duration else false.
     */
    boolean mustGenerateDuration();

    /**
     * Indicate if the user want to generate the resolution.
     *
     * @return true if the user want to generate the resolution else false.
     */
    boolean mustGenerateResolution();

    /**
     * Indicate if the user want to generate the image.
     *
     * @return true if the user want to generate the image else false.
     */
    boolean mustGenerateImage();

    /**
     * Indicate if the user want include the subcategories or not.
     *
     * @return true if the user want to generate the informations for the subcategories to else false.
     */
    boolean areSubCategoriesIncluded();

    /**
     * Return the selected category.
     *
     * @return The selected category.
     */
    Category getSelectedCategory();
}