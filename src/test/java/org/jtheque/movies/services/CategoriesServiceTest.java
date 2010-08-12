package org.jtheque.movies.services;

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

import org.jtheque.collections.DataCollection;
import org.jtheque.collections.DaoCollections;
import org.jtheque.collections.impl.DataCollectionImpl;
import org.jtheque.movies.services.able.ICategoriesService;
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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import static org.junit.Assert.*;

/**
 * @author Baptiste Wicht
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class CategoriesServiceTest extends AbstractDBUnitTest {
    @Resource
    private ICategoriesService categoriesService;

    @Resource
    private DaoCollections daoCollections;

    @Resource
    private IPrimaryUtils primaryUtils;

    @Resource
    private DataSource dataSource;

    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);
    }

    public CategoriesServiceTest() {
        super("categories.xml");
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);

        primaryUtils.setPrimaryImpl("Movies");

        DataCollection collection = new DataCollectionImpl();
        collection.setId(1);
        collection.setPassword("");
        collection.setProtection(false);
        collection.setTitle("Collection 1");

        daoCollections.setCurrentCollection(collection);
    }

    @Test
    public void initOK() {
        assertNotNull(categoriesService);
    }

    @Test
    public void exists() {
        assertFalse(categoriesService.exists("Category 00"));
        assertTrue(categoriesService.exists("Category 1"));
    }

    @Test
    public void existsInOtherCategory() {
        assertFalse(categoriesService.existsInOtherCategory("Category 1", categoriesService.getCategory("Category 1")));
        assertTrue(categoriesService.existsInOtherCategory("Category 2", categoriesService.getCategory("Category 1")));
        assertTrue(categoriesService.existsInOtherCategory("Category 1", categoriesService.getCategory("Category 2")));
    }

    @Test
    public void getSubCategories() {
        assertEquals(0, categoriesService.getSubCategories(null).size());
        assertEquals(0, categoriesService.getSubCategories(categoriesService.getCategory("Category 3")).size());
        assertEquals(1, categoriesService.getSubCategories(categoriesService.getCategory("Category 2")).size());
        assertEquals(2, categoriesService.getSubCategories(categoriesService.getCategory("Category 1")).size());
    }

    @Test
    public void getDataType() {
        assertEquals("Categories", categoriesService.getDataType());
    }
}
