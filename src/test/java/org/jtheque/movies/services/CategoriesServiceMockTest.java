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

import org.apache.log4j.Logger;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.impl.CategoriesService;
import org.jtheque.utils.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

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
            Logger.getLogger(getClass()).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Logger.getLogger(getClass()).error(e.getMessage(), e);
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