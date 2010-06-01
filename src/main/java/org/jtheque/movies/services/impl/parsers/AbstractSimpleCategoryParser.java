package org.jtheque.movies.services.impl.parsers;

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;

import javax.annotation.Resource;
import javax.swing.JComponent;

import java.util.ArrayList;
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
 * An abstract simple file parser.
 *
 * @author Baptiste Wicht
 */
abstract class AbstractSimpleCategoryParser implements FileParser {
    private final Collection<Category> categories = new ArrayList<Category>(5);

    @Resource
    private ICategoriesService categoriesService;

    /**
     * Add category to the list. If there category doesn't exists, it will be created.
     *
     * @param name The name of the category.
     */
    void addCategory(String name) {
        if (categoriesService.exists(name)) {
            categories.add(categoriesService.getCategory(name));
        } else {
            Category category = categoriesService.getEmptyCategory();
            category.setTitle(name);

            categories.add(category);
        }
    }

    @Override
    public Collection<Category> getExtractedCategories() {
        return categories;
    }

    @Override
    public boolean hasCustomView() {
        return false;
    }

    @Override
    public JComponent getCustomView() {
        return null;
    }
}