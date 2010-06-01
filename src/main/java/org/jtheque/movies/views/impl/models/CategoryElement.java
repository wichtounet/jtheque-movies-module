package org.jtheque.movies.views.impl.models;

import org.jtheque.movies.MoviesResources;
import org.jtheque.primary.utils.views.tree.TreeElement;
import org.jtheque.resources.able.IResourceService;

import javax.swing.Icon;

import java.util.ArrayList;
import java.util.List;

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
 * A category element on the tree.
 *
 * @author Baptiste Wicht
 */
public final class CategoryElement implements TreeElement {
    private final List<TreeElement> elements = new ArrayList<TreeElement>(20);
    private final String name;

    /**
     * Constructs a new CategoryElement.
     *
     * @param name The name of element
     */
    public CategoryElement(String name) {
        super();

        this.name = name;
    }

    @Override
    public String getElementName() {
        return name;
    }

    @Override
    public Icon getIcon(IResourceService resourceService) {
        return resourceService.getIcon(MoviesResources.BOX_ICON);
    }

    @Override
    public boolean isRoot() {
        return elements.isEmpty();
    }

    @Override
    public boolean isCategory() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return elements.isEmpty();
    }

    @Override
    public TreeElement getChild(int index) {
        return elements.get(index);
    }

    @Override
    public int getChildCount() {
        return elements.size();
    }

    @Override
    public int indexOf(TreeElement element) {
        return elements.indexOf(element);
    }

    @Override
    public void add(TreeElement element) {
        elements.add(element);
    }

    @Override
    public void addAll(Iterable<? extends TreeElement> elements) {
        for (TreeElement element : elements) {
            add(element);
        }
    }

    @Override
    public void clear() {
        elements.clear();
    }
}