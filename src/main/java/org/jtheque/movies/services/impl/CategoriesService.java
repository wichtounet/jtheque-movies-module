package org.jtheque.movies.services.impl;

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

import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.persistence.able.DataListener;
import org.jtheque.utils.collections.CollectionUtils;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A categories service implementation.
 *
 * @author Baptiste Wicht
 */
public final class CategoriesService implements ICategoriesService {
    @Resource
    private IDaoCategories daoCategories;

    @Override
    public Collection<Category> getCategories() {
        return daoCategories.getCategories();
    }

    @Override
    @Transactional
    public boolean delete(Category category) {
        return daoCategories.delete(category);
    }

    @Override
    @Transactional
    public void create(Category category) {
        daoCategories.create(category);
    }

    @Override
    @Transactional
    public void save(Category category) {
        daoCategories.save(category);
    }

    @Override
    public boolean exists(String category) {
        return daoCategories.getCategory(category) != null;
    }

    @Override
    public Category getCategory(String name) {
        return daoCategories.getCategory(name);
    }

    @Override
    public Category getEmptyCategory() {
        return daoCategories.create();
    }

    @Override
    public boolean existsInOtherCategory(String title, Category category) {
        for (Category other : daoCategories.getCategories()) {
            if (other.getId() != category.getId() && title.equals(other.getTitle())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Collection<Category> getSubCategories(Category category) {
        if (category == null) {
            return CollectionUtils.emptyList();
        }

        Collection<Category> categories = new ArrayList<Category>(20);

        for (Category cat : getCategories()) {
            if (cat.getParent() != null && cat.getParent().getId() == category.getId()) {
                categories.add(cat);
                categories.addAll(getSubCategories(cat));
            }
        }

        return categories;
    }

    @Override
    public Collection<Category> getDatas() {
        return getCategories();
    }

    @Override
    public void addDataListener(DataListener listener) {
        daoCategories.addDataListener(listener);
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }

    @Override
    @Transactional
    public void clearAll() {
        daoCategories.clearAll();
    }
}