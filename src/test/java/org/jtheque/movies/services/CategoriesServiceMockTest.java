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

import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.impl.CategoriesService;
import org.jtheque.persistence.able.DataListener;
import org.jtheque.utils.collections.CollectionUtils;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

import static org.easymock.EasyMock.*;

/**
 * @author Baptiste Wicht
 */
public class CategoriesServiceMockTest {
    private ICategoriesService categoriesService;

    private IDaoCategories daoCategories;

    @Before
    public void setUp() {
        categoriesService = new CategoriesService();

        daoCategories = createMock(IDaoCategories.class);

        try {
            Field field = CategoriesService.class.getDeclaredField("daoCategories");

            field.setAccessible(true);

            field.set(categoriesService, daoCategories);
        } catch (NoSuchFieldException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @Test
    public void testGetCategories() {
        expect(daoCategories.getCategories()).andReturn(CollectionUtils.<Category>emptyList());

        replay(daoCategories);

        categoriesService.getCategories();

        verify(daoCategories);
    }

    @Test
    public void testDelete() {
        expect(daoCategories.delete(new CategoryImpl())).andReturn(true);

        replay(daoCategories);

        categoriesService.delete(new CategoryImpl());

        verify(daoCategories);
    }

    @Test
    public void testCreate() {
        daoCategories.create(new CategoryImpl());

        replay(daoCategories);

        categoriesService.create(new CategoryImpl());

        verify(daoCategories);
    }

    @Test
    public void testSave() {
        daoCategories.save(new CategoryImpl());

        replay(daoCategories);

        categoriesService.save(new CategoryImpl());

        verify(daoCategories);
    }

    @Test
    public void testGetCategory() {
        expect(daoCategories.getCategory("asdf")).andReturn(null);

        replay(daoCategories);

        categoriesService.getCategory("asdf");

        verify(daoCategories);
    }

    @Test
    public void testGetEmptyCategory() {
        expect(daoCategories.create()).andReturn(new CategoryImpl(""));

        replay(daoCategories);

        categoriesService.getEmptyCategory();

        verify(daoCategories);
    }

    @Test
    public void testGetDatas() {
        expect(daoCategories.getCategories()).andReturn(CollectionUtils.<Category>emptyList());

        replay(daoCategories);

        categoriesService.getDatas();

        verify(daoCategories);
    }

    @Test
    public void testClearAll() {
        daoCategories.clearAll();

        replay(daoCategories);

        categoriesService.clearAll();

        verify(daoCategories);
    }

    @Test
    public void testAddDataListener() {
        final DataListener listener = new DataListener() {
            @Override
            public void dataChanged() {
            }
        };

        daoCategories.addDataListener(listener);

        replay(daoCategories);

        categoriesService.addDataListener(listener);

        verify(daoCategories);
    }
}