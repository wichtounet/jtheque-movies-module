package org.jtheque.movies.persistence.dao.impl;

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

import org.jtheque.core.managers.persistence.GenericDao;
import org.jtheque.core.managers.persistence.Query;
import org.jtheque.core.managers.persistence.QueryMapper;
import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.core.managers.persistence.context.IDaoPersistenceContext;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.primary.dao.able.IDaoCollections;
import org.jtheque.primary.od.able.Collection;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Data Access Object implementation for categories.
 *
 * @author Baptiste Wicht
 */
public final class DaoCategories extends GenericDao<Category> implements IDaoCategories {
    private final ParameterizedRowMapper<Category> rowMapper = new CategoryRowMapper();
    private final QueryMapper queryMapper = new CategoryQueryMapper();

    private boolean cacheEntirelyLoaded;

    @Resource
    private IDaoPersistenceContext daoPersistenceContext;

    @Resource
    private IDaoCollections daoCollections;

    /**
     * Construct a new DaoCategories.
     */
    public DaoCategories(){
        super(TABLE);
    }

    @Override
    public java.util.Collection<Category> getCategories(){
        List<Category> categories = (List<Category>) getCategories(daoCollections.getCurrentCollection());

        Collections.sort(categories);

        return categories;
    }

    @Override
    public Category getCategory(int id){
        return get(id);
    }

    /**
     * Return all the categories of the specified collection.
     *
     * @param collection The collection to collect categories from.
     * @return A List containing all the categories of the collection.
     */
    private java.util.Collection<Category> getCategories(Collection collection){
        if (collection == null || !collection.isSaved()){
            return getAll();
        }

        load();

        java.util.Collection<Category> categories = new ArrayList<Category>(getCache().size() / 2);

        for (Category category : getCache().values()){
            if (category.getTheCollection().getId() == collection.getId()){
                categories.add(category);
            }
        }

        return categories;
    }

    @Override
    public Category getCategory(String name){
        for (Category category : getCategories()){
            if (category.getTitle().equals(name)){
                return category;
            }
        }

        return null;
    }

    @Override
    public Category createCategory(){
        return new CategoryImpl();
    }

    @Override
    protected ParameterizedRowMapper<Category> getRowMapper(){
        return rowMapper;
    }

    @Override
    protected QueryMapper getQueryMapper(){
        return queryMapper;
    }

    @Override
    public void create(Category entity){
        entity.setTheCollection(daoCollections.getCurrentCollection());

        super.create(entity);
    }

    @Override
    protected void loadCache(){
        java.util.Collection<Category> categories = daoPersistenceContext.getSortedList(TABLE, rowMapper);

        for (Category category : categories){
            getCache().put(category.getId(), category);
        }

		for (Category category : categories){
            category.setParent(getCache().get(category.getTemporaryParent()));
        }

        setEntirelyLoaded();
    }

	@Override
    protected void load(int i){
        Category category = daoPersistenceContext.getDataByID(TABLE, i, rowMapper);

        getCache().put(i, category);
    }

    /**
     * Set if the cache has been entirely loaded or not.
     */
    protected void setEntirelyLoaded() {
        cacheEntirelyLoaded = true;

		setCacheEntirelyLoaded();
    }

    /**
     * A row mapper to map a resultset to a category.
     *
     * @author Baptiste Wicht
     */
    private final class CategoryRowMapper implements ParameterizedRowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException{
            Category category = createCategory(rs.getString("TITLE"));

            category.setId(rs.getInt("ID"));
            category.setTheCollection(daoCollections.getCollection(rs.getInt("THE_COLLECTION_FK")));

			if(cacheEntirelyLoaded){
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
         * @return A category with the given title.
         */
        private Category createCategory(String title){
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
        public Query constructInsertQuery(Entity entity){
			return new Query(
					"INSERT INTO " + TABLE + " (TITLE, THE_PARENT_FK, THE_COLLECTION_FK) VALUES(?,?,?)",
					fillArray((Category) entity, false));
        }

        @Override
        public Query constructUpdateQuery(Entity entity){
			return new Query(
					"UPDATE " + TABLE + " SET TITLE = ?, THE_PARENT_FK = ?, THE_COLLECTION_FK = ? WHERE ID = ?",
					fillArray((Category) entity, true));
        }

		/**
		 * Fill the array with the informations of the category.
		 *
		 * @param category The category to use to fill the array.
		 * @param id Indicate if we must add the id to the array.
		 *
		 * @return The filled array.
		 */
		private static Object[] fillArray(Category category, boolean id){
			Object[] values = new Object[3 + (id ? 1 : 0)];

			values[0] = category.getTitle();
			values[1] = category.getParent() == null ? null : category.getParent().getId();
			values[2] = category.getTheCollection().getId();

			if (id){
				values[3] = category.getId();
			}

			return values;
		}
    }
}