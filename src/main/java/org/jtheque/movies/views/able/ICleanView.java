package org.jtheque.movies.views.able;

import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.movies.views.able.models.ICleanModel;
import org.jtheque.ui.able.View;

import java.util.Collection;

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
public interface ICleanView extends View {
    /**
     * Return all the selected cleaners.
     *
     * @return A Collection containing all the selected cleaners.
     */
    Collection<NameCleaner> getSelectedCleaners();

    /**
     * Indicate if the subcategories are included in the clean process.
     *
     * @return true if we must include the subcategories else false.
     */
    boolean areSubCategoriesIncluded();

    @Override
    ICleanModel getModel();
}