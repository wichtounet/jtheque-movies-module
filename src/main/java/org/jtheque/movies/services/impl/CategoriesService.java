package org.jtheque.movies.services.impl;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
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
        return daoCategories.createCategory();
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
            if (cat.getParent() == category) {
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