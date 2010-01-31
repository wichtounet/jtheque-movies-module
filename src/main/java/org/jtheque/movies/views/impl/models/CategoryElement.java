package org.jtheque.movies.views.impl.models;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.primary.view.impl.models.tree.TreeElement;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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
    public Icon getIcon() {
        return Managers.getManager(IResourceManager.class).getIcon(IMoviesModule.IMAGES_BASE_NAME, "box", ImageType.PNG);
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