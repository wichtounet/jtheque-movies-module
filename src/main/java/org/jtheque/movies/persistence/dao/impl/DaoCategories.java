package org.jtheque.movies.persistence.dao.impl;

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

import org.jtheque.collections.able.DataCollection;
import org.jtheque.collections.able.DaoCollections;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.persistence.able.DaoPersistenceContext;
import org.jtheque.persistence.able.Entity;
import org.jtheque.persistence.able.QueryMapper;
import org.jtheque.persistence.utils.CachedJDBCDao;
import org.jtheque.persistence.utils.EntityUtils;
import org.jtheque.persistence.utils.Query;
import org.jtheque.utils.collections.CollectionUtils;

import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A Data Access Object implementation for categories.
 *
 * @author Baptiste Wicht
 */
public final class DaoCategories extends CachedJDBCDao<Category> implements IDaoCategories {
    private final RowMapper<Category> rowMapper = new CategoryRowMapper();
    private final QueryMapper queryMapper = new CategoryQueryMapper();

    @Resource
    private DaoPersistenceContext daoPersistenceContext;

    @Resource
    private DaoCollections daoCollections;

    /**
     * Construct a new DaoCategories.
     */
    public DaoCategories() {
        super(TABLE);
    }

    @Override
    public Collection<Category> getCategories() {
        List<Category> categories = getCategories(daoCollections.getCurrentCollection());

        Collections.sort(categories);

        return categories;
    }

    @Override
    public Category getCategoryByTemporaryId(int id) {
        return EntityUtils.getByTemporaryId(getAll(), id);
    }

    @Override
    public Category getCategory(int id) {
        return get(id);
    }

    /**
     * Return all the categories of the specified collection.
     *
     * @param collection The collection to collect categories from.
     *
     * @return A List containing all the categories of the collection.
     */
    private List<Category> getCategories(DataCollection collection) {
        if (collection == null || !collection.isSaved()) {
            return (List<Category>) getAll();
        }

        load();

        List<Category> categories = new ArrayList<Category>(getCache().values());

        CollectionUtils.filter(categories, new CollectionFilter<Category>(collection));

        return categories;
    }

    @Override
    public Category getCategory(String name) {
        for (Category category : getCategories()) {
            if (category.getTitle().equals(name)) {
                return category;
            }
        }

        return null;
    }

    @Override
    public Category create() {
        return new CategoryImpl();
    }

    @Override
    protected QueryMapper getQueryMapper() {
        return queryMapper;
    }

    @Override
    protected RowMapper<Category> getRowMapper() {
        return rowMapper;
    }

    @Override
    public void save(Category entity) {
        if (!entity.isSaved()) {
            entity.setTheCollection(daoCollections.getCurrentCollection());
        }

        super.save(entity);
    }

    @Override
    protected void loadCache() {
        Collection<Category> categories = daoPersistenceContext.getSortedList(TABLE, rowMapper);

        //First we loads the categories
        for (Category category : categories) {
            getCache().put(category.getId(), category);
        }

        //Then we solve the links between categories
        for (Category category : categories) {
            category.setParent(getCache().get(category.getTemporaryParent()));
        }
    }

    /**
     * A row mapper to map a resultset to a category.
     *
     * @author Baptiste Wicht
     */
    private final class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category category = createCategory(rs.getString("TITLE"));

            category.setId(rs.getInt("ID"));
            category.setTheCollection(daoCollections.getCollection(rs.getInt("THE_COLLECTION_FK")));

            if (isCacheEntirelyLoaded()) {
                category.setParent(getCategory(rs.getInt("THE_PARENT_FK")));
            } else {
                category.setTemporaryParent(rs.getInt("THE_PARENT_FK"));
            }

            return category;
        }

        /**
         * Create a category with the given title.
         *
         * @param title The title of the new category.
         *
         * @return A category with the given title.
         */
        private Category createCategory(String title) {
            return new CategoryImpl(title);
        }
    }

    /**
     * A query mapper to map category to requests.
     *
     * @author Baptiste Wicht
     */
    private static final class CategoryQueryMapper implements QueryMapper {
        @Override
        public Query constructInsertQuery(Entity entity) {
            return new Query(
                    "INSERT INTO " + TABLE + " (TITLE, THE_PARENT_FK, THE_COLLECTION_FK) VALUES(?,?,?)",
                    fillArray((Category) entity, false));
        }

        @Override
        public Query constructUpdateQuery(Entity entity) {
            return new Query(
                    "UPDATE " + TABLE + " SET TITLE = ?, THE_PARENT_FK = ?, THE_COLLECTION_FK = ? WHERE ID = ?",
                    fillArray((Category) entity, true));
        }

        /**
         * Fill the array with the informations of the category.
         *
         * @param category The category to use to fill the array.
         * @param id       Indicate if we must add the id to the array.
         *
         * @return The filled array.
         */
        private static Object[] fillArray(Category category, boolean id) {
            Object[] values = new Object[3 + (id ? 1 : 0)];

            values[0] = category.getTitle();
            values[1] = category.getParent() == null ? null : category.getParent().getId();
            values[2] = category.getTheCollection().getId();

            if (id) {
                values[3] = category.getId();
            }

            return values;
        }
    }
}