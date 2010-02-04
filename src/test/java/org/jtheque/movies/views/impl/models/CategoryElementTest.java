package org.jtheque.movies.views.impl.models;

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

import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CategoryElementTest {
    @Test
    public void getElementName(){
        CategoryElement element = new CategoryElement("Super category");

        assertEquals("Super category", element.getElementName());
        assertEquals(0, element.getChildCount());
        assertTrue(element.isLeaf());
        assertTrue(element.isCategory());
    }

    @Test
    public void add(){
        TreeElement element = new CategoryElement("Test");

        TreeElement child1 = new CategoryElement("Child 1");
        TreeElement child2 = new CategoryElement("Child 2");

        element.add(child1);
        element.add(child2);

        assertFalse(element.isLeaf());
        assertEquals(2, element.getChildCount());
        assertEquals(child1, element.getChild(0));
        assertEquals(child2, element.getChild(1));

        assertEquals(0, element.indexOf(child1));
        assertEquals(1, element.indexOf(child2));
    }

    @Test
    public void addAll(){
        TreeElement element = new CategoryElement("Test");

        TreeElement child1 = new CategoryElement("Child 1");
        TreeElement child2 = new CategoryElement("Child 2");

        element.addAll(Arrays.asList(child1, child2));

        assertFalse(element.isLeaf());
        assertEquals(2, element.getChildCount());
        assertEquals(child1, element.getChild(0));
        assertEquals(child2, element.getChild(1));
    }

    @Test
    public void clear(){
        TreeElement element = new CategoryElement("Test");

        TreeElement child1 = new CategoryElement("Child 1");
        TreeElement child2 = new CategoryElement("Child 2");

        element.addAll(Arrays.asList(child1, child2));

        element.clear();

        assertEquals(0, element.getChildCount());
        assertTrue(element.isLeaf());
    }
}
