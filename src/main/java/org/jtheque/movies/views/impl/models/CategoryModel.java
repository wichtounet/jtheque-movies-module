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
import org.jtheque.movies.views.impl.models.able.ICategoryModel;
import org.jtheque.primary.view.able.ViewMode;

/**
 * A model for the kind view.
 *
 * @author Baptiste Wicht
 */
public final class CategoryModel implements ICategoryModel {
    private ViewMode state = ViewMode.NEW;
    private Category category;

    @Override
    public ViewMode getState(){
        return state;
    }

    @Override
    public void setState(ViewMode state){
        this.state = state;
    }

    @Override
    public Category getCategory(){
        return category;
    }

    /**
     * Set the category of the model.
     *
     * @param category The current category.
     */
    @Override
    public void setCategory(Category category){
        this.category = category;
    }
}