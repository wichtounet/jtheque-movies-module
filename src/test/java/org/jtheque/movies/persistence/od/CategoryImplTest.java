package org.jtheque.movies.persistence.od;

import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.persistence.Entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

/**
 * @author Baptiste Wicht
 */
public class CategoryImplTest {
    private CategoryImpl category;

    @Before
    public void setUp() {
        category = new CategoryImpl();
    }

    @Test
    public void testHashCode() {
        category.setId(123);
        category.setTitle("Test");

        Entity category2 = new CategoryImpl("Test");
        category2.setId(123);

        assertEquals(category.hashCode(), category2.hashCode());

        category.setTitle("Test 1234");

        assertFalse(category.hashCode() == category2.hashCode());
    }

    @Test
    public void testToString() {
        category.setTitle("Super title");

        assertEquals("Super title", category.toString());

        category.setTitle("Super title 2");

        assertEquals("Super title 2", category.toString());
    }

    @Test
    public void equals() {
        category.setId(123);
        category.setTitle("Test");

        Entity category2 = new CategoryImpl("Test");
        category2.setId(123);

        assertEquals(category, category2);
        assertEquals(category2, category);

        category.setTitle("Test 1234");

        assertFalse(category.equals(category2));
        assertFalse(category2.equals(category));
    }

    @Test
    public void equalsNull() {
        assertFalse(category.equals(null));
    }
}
