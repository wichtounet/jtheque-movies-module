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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.managers.view.impl.components.model.SimpleListModel;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;

import javax.annotation.Resource;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A list model for actors.
 *
 * @author Baptiste Wicht
 */
public final class CategoriesListModel extends DefaultListModel implements DataListener {
    @Resource
    private ICategoriesService categoriesService;

    private SimpleListModel<Category> linkedModel;

    private List<Category> categories;

    /**
     * Construct a new ActorsListModel.
     */
    public CategoriesListModel() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        categoriesService.addDataListener(this);
        categories = new ArrayList<Category>(categoriesService.getCategories());
    }

    @Override
    public Object getElementAt(int index) {
        return categories.get(index);
    }

    @Override
    public Object get(int index) {
        return categories.get(index);
    }

    @Override
    public int getSize() {
        return categories.size();
    }

    @Override
    public Object remove(int i) {
        Category category = categories.remove(i);
        fireIntervalRemoved(this, i, i);
        return category;
    }

    @Override
    public void addElement(Object obj) {
        categories.add((Category) obj);
        fireIntervalAdded(this, getSize(), getSize());
    }

    @Override
    public void removeAllElements() {
        categories.clear();
        fireContentsChanged(this, 0, getSize());
    }

    @Override
    public boolean removeElement(Object obj) {
        Category category = (Category) obj;

        int index = categories.indexOf(category);

        if (index != -1) {
            boolean remove = categories.remove(category);
            fireIntervalRemoved(this, index, index);
            return remove;
        }

        return false;
    }

    @Override
    public void dataChanged() {
        reload();
    }

    /**
     * Reload the model.
     */
    public void reload() {
        categories = new ArrayList<Category>(categoriesService.getCategories());

        if (linkedModel != null) {
            for (Category category : linkedModel.getObjects()) {
                removeElement(category);
            }
        }

        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Set the linked model of this model. It seems that, when we must reload the data, we remove from this model the data of
     * the linked model.
     *
     * @param linkedModel The model to link to.
     */
    public void setLinkedModel(SimpleListModel<Category> linkedModel) {
        this.linkedModel = linkedModel;
    }
}