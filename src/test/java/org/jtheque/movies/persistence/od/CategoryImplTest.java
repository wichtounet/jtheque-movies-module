package org.jtheque.movies.persistence.od;

import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

/**
 * @author Baptiste Wicht
 */
public class CategoryImplTest {
    private CategoryImpl category;

    @Before
    public void setUp(){
        category = new CategoryImpl();
    }

    @Test
    public void testHashCode(){
        category.setId(123);
        category.setTitle("Test");

        Entity category2 = new CategoryImpl("Test");
        category2.setId(123);

        assertEquals(category.hashCode(), category2.hashCode());

        category.setTitle("Test 1234");

        assertFalse(category.hashCode() == category2.hashCode());
    }

	@Test
	public void testToString(){
		category.setTitle("Super title");

		assertEquals("Super title", category.toString());

		category.setTitle("Super title 2");

		assertEquals("Super title 2", category.toString());
	}

    @Test
    public void equals(){
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
}
