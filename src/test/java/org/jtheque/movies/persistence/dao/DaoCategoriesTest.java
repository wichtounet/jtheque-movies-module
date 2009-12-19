package org.jtheque.movies.persistence.dao;

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

import org.jtheque.core.utils.test.AbstractDBUnitTest;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.primary.dao.able.IDaoCollections;
import org.jtheque.primary.od.impl.CollectionImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * A test for DaoCategories.
 *
 * @author Baptiste Wicht
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class DaoCategoriesTest extends AbstractDBUnitTest {
    @Resource
    private IDaoCategories daoCategories;

    @Resource
    private IDaoCollections daoCollections;

    @Resource
    private DataSource dataSource;

    public DaoCategoriesTest(){
        super("JTheque Collections/JTheque Movies Module/src/test/resources/org/jtheque/movies/persistence/categories.xml");
    }

    @PostConstruct
    public void init(){
        initDB(dataSource);

        PrimaryUtils.setPrimaryImpl("Movies");

        org.jtheque.primary.od.able.Collection collection = new CollectionImpl();
        collection.setId(1);
        collection.setPassword("");
        collection.setProtection(false);
        collection.setTitle("Collection 1");
        collection.setPrimaryImpl("Movies");

        daoCollections.setCurrentCollection(collection);
    }

    @Test
    public void initOK(){
        assertNotNull(daoCategories);
    }

    @Test
    public void getCategoryById(){
        Category cat = daoCategories.getCategory(2);

        assertNotNull(cat);
        assertEquals("Catégorie 2", cat.getTitle());
        assertEquals(1, cat.getTheCollection().getId());
    }

    @Test
    public void getCategories(){
        Collection<Category> categories = daoCategories.getCategories();

        assertEquals(2, categories.size());
    }

    @Test
    public void getExistingCategoryByName(){
        Category cat = daoCategories.getCategory("Catégorie 2");

        assertNotNull(cat);
        assertEquals(2, cat.getId());
        assertEquals("Catégorie 2", cat.getTitle());
    }

    @Test
    public void getNonExistingCategoryByName(){
        Category cat = daoCategories.getCategory("Peutêtre 3");

        assertNull(cat);
    }

    @Test
    public void getFalseCollectionCategoryByName(){
        Category cat = daoCategories.getCategory("Catégorie 3");

        assertNull(cat);
    }

    @Test
    public void createCategory(){
        Category cat = new CategoryImpl("Created category");

        daoCategories.create(cat);

        assertEquals(4, getRowCount("T_MOVIE_CATEGORIES"));
    }

    @Test
    public void saveCategory(){
        Category cat = daoCategories.getCategory(1);
        cat.setTitle("New title");

        daoCategories.save(cat);

        assertEquals("New title", getValue("T_MOVIE_CATEGORIES", 0, "TITLE").toString());
    }

    @Test
    public void deleteCategory(){
        Category cat = daoCategories.getCategory(1);
        daoCategories.delete(cat);

        assertEquals(2, getTable("T_MOVIE_CATEGORIES").getRowCount());
        assertNull(daoCategories.getCategory(1));
    }

    @Test
    public void clearAll(){
        daoCategories.clearAll();

        assertEquals(0, getTable("T_MOVIE_CATEGORIES").getRowCount());
        assertEquals(0, daoCategories.getCategories().size());
    }
}