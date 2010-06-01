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

import org.jtheque.primary.utils.views.tree.TreeElement;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CategoryElementTest {
    @Test
    public void getElementName() {
        TreeElement element = new CategoryElement("Super category");

        assertEquals("Super category", element.getElementName());
        assertEquals(0, element.getChildCount());
        assertTrue(element.isLeaf());
        assertTrue(element.isRoot());
        assertTrue(element.isCategory());
    }

    @Test
    public void add() {
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
    public void addAll() {
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
    public void clear() {
        TreeElement element = new CategoryElement("Test");

        TreeElement child1 = new CategoryElement("Child 1");
        TreeElement child2 = new CategoryElement("Child 2");

        element.addAll(Arrays.asList(child1, child2));

        element.clear();

        assertEquals(0, element.getChildCount());
        assertTrue(element.isLeaf());
    }
}
