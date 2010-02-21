package org.jtheque.movies.services;

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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.jtheque.core.managers.collection.Collection;
import org.jtheque.core.utils.test.AbstractDBUnitTest;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.core.managers.collection.IDaoCollections;
import org.jtheque.core.managers.collection.CollectionImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

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
    private IDaoCollections daoCollections;

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

        PrimaryUtils.setPrimaryImpl("Movies");

        Collection collection = new CollectionImpl();
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
    public void exists(){
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
    public void getDataType(){
        assertEquals("Categories", categoriesService.getDataType());
    }
}
