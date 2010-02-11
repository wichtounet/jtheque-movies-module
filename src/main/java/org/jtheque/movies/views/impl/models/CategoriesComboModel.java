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

import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;

import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A categories combo box model with the data in a cache.
 *
 * @author Baptiste Wicht
 */
public final class CategoriesComboModel extends DefaultComboBoxModel implements DataListener {
	private final ICategoriesService categoriesService;

	private List<Category> datas;

    private final Category emptyCategory;

    /**
	 * Construct a new CategoriesComboMode.
	 */
	public CategoriesComboModel(){
		super();

		categoriesService = CoreUtils.getBean("categoriesService");

        emptyCategory = categoriesService.getEmptyCategory();

		datas = new ArrayList<Category>(categoriesService.getDatas());
        datas.add(emptyCategory);

        emptyCategory.setTitle(" ");

		categoriesService.addDataListener(this);
	}

    @Override
	public Object getElementAt(int index){
		return datas.get(index);
	}

	@Override
	public int getSize(){
		return datas.size();
	}

    @Override
    public int getIndexOf(Object anObject) {
        if(anObject == null){
            return super.getIndexOf(emptyCategory);
        }

        return super.getIndexOf(anObject);
    }

    /**
	 * Return the selected data in the model.
	 *
	 * @return The data who's selected.
	 */
	public Category getSelectedCategory(){
        Category selected = (Category) getSelectedItem();

		return selected == emptyCategory ? null : selected;
	}

    @Override
    public void setSelectedItem(Object anObject) {
        if(anObject == null){
            super.setSelectedItem(emptyCategory);
        }

        super.setSelectedItem(anObject);
    }

    @Override
	public void dataChanged(){
		datas = new ArrayList<Category>(categoriesService.getDatas());
        datas.add(emptyCategory);

		fireContentsChanged(this, 0, getSize());
	}
}