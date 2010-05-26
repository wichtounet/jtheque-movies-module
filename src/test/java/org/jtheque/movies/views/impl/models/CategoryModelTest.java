package org.jtheque.movies.views.impl.models;

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

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.movies.views.able.models.ICategoryModel;
import org.jtheque.primary.able.views.ViewMode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryModelTest {
    private ICategoryModel model;

    @Before
    public void setUp(){
        model = new CategoryModel();
    }

    @Test
    public void testGetState() throws Exception {
        assertEquals(ViewMode.NEW, model.getState());

        model.setState(ViewMode.AUTO);

        assertEquals(ViewMode.AUTO, model.getState());
    }

    @Test
    public void testGetCategory() throws Exception {
        Category category = new CategoryImpl("");

        assertNull(model.getCategory());

        model.setCategory(category);

        assertSame(category, model.getCategory());
    }
}