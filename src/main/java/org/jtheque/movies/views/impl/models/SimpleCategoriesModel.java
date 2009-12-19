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

import org.jtheque.movies.persistence.od.able.Category;

import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple model for actors.
 *
 * @author Baptiste Wicht
 */
public final class SimpleCategoriesModel extends DefaultListModel {
    private static final long serialVersionUID = 627034111249354845L;

    private final List<Category> categories;

    /**
     * Construct a new SimpleActorsModel.
     */
    public SimpleCategoriesModel(){
        super();

        categories = new ArrayList<Category>(6);
    }

    @Override
    public Object getElementAt(int index){
        return categories.get(index);
    }

    @Override
    public Object get(int index){
        return categories.get(index);
    }

    @Override
    public int getSize(){
        return categories.size();
    }

    @Override
    public Object remove(int index){
        Category category = categories.remove(index);
        fireIntervalRemoved(this, index, index);
        return category;
    }

    @Override
    public void addElement(Object obj){
        categories.add((Category) obj);
        fireIntervalAdded(this, getSize(), getSize());
    }

    /**
     * Add the elements to the model.
     *
     * @param elements The elements to add.
     */
    public void addElements(Iterable<Category> elements){
        int index = categories.size();

        for (Category category : elements){
            categories.add(category);
        }

        fireIntervalAdded(this, index, getSize());
    }

    @Override
    public void clear(){
        categories.clear();
    }

    @Override
    public boolean removeElement(Object obj){
        Category category = (Category) obj;

        int index = categories.indexOf(category);
        boolean remove = categories.remove(category);
        fireIntervalRemoved(this, index, index);
        return remove;
    }

    @Override
    public void removeAllElements(){
        categories.clear();
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Return the categories.
     *
     * @return A List containing all the categories of the model.
     */
    public Collection<Category> getCategories(){
        return categories;
    }
}