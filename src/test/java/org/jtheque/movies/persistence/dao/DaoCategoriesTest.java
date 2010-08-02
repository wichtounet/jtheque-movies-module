package org.jtheque.movies.persistence.dao;

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

import org.jtheque.collections.able.DaoCollections;
import org.jtheque.collections.impl.CollectionImpl;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.primary.able.IPrimaryUtils;
import org.jtheque.utils.unit.db.AbstractDBUnitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import java.util.Collection;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

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
    private DaoCollections daoCollections;

    @Resource
    private DataSource dataSource;

    @Resource
    private IPrimaryUtils primaryUtils;

    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);
    }

    public DaoCategoriesTest() {
        super("categories.xml");
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);

        primaryUtils.setPrimaryImpl("Movies");

        org.jtheque.collections.able.Collection collection = new CollectionImpl();
        collection.setId(1);
        collection.setPassword("");
        collection.setProtection(false);
        collection.setTitle("Collection 1");

        daoCollections.setCurrentCollection(collection);
    }

    @Test
    public void initOK() {
        assertNotNull(daoCategories);
    }

    @Test
    public void getCategories() {
        Collection<Category> categories = daoCategories.getCategories();

        assertEquals(2, categories.size());
    }

    @Test
    public void getCategoriesWithNoCollection() {
        daoCollections.setCurrentCollection(null);

        Collection<Category> categories = daoCategories.getCategories();

        assertEquals(3, categories.size());
    }

    @Test
    public void getCategoryById() {
        Category cat = daoCategories.getCategory(2);

        assertNotNull(cat);
        assertEquals("Category 2", cat.getTitle());
        assertEquals(1, cat.getTheCollection().getId());
    }

    @Test
    public void getExistingCategoryByName() {
        Category cat = daoCategories.getCategory("Category 2");

        assertNotNull(cat);
        assertEquals(2, cat.getId());
        assertEquals("Category 2", cat.getTitle());
    }

    @Test
    public void getNonExistingCategoryByName() {
        Category cat = daoCategories.getCategory("Perhaps 3");

        assertNull(cat);
    }

    @Test
    public void getFalseCollectionCategoryByName() {
        Category cat = daoCategories.getCategory("Category 3");

        assertNull(cat);
    }

    @Test
    public void createCategory() {
        Category cat = new CategoryImpl("Created category");

        daoCategories.create(cat);

        assertEquals(4, getRowCount("T_MOVIE_CATEGORIES"));
    }

    @Test
    public void saveCategory() {
        Category cat = daoCategories.getCategory(1);
        cat.setTitle("New title");

        daoCategories.save(cat);

        assertEquals("New title", getValue("T_MOVIE_CATEGORIES", 0, "TITLE").toString());
    }

    @Test
    public void saveCategoryWithNoParent() {
        Category cat = daoCategories.getCategory(1);
        cat.setTitle("New title");
        cat.setParent(null);

        daoCategories.save(cat);

        assertEquals("New title", getValue("T_MOVIE_CATEGORIES", 0, "TITLE").toString());
    }

    @Test
    public void deleteCategory() {
        Category cat = daoCategories.getCategory(1);
        daoCategories.delete(cat);

        assertEquals(2, getTable("T_MOVIE_CATEGORIES").getRowCount());
        assertNull(daoCategories.getCategory(1));
    }

    @Test
    public void clearAll() {
        daoCategories.clearAll();

        assertEquals(0, getTable("T_MOVIE_CATEGORIES").getRowCount());
        assertEquals(0, daoCategories.getCategories().size());
    }
}