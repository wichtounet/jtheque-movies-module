package org.jtheque.movies.views.able.models;

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

import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.primary.view.able.ViewMode;

/**
 * @author Baptiste Wicht
 */
public interface ICategoryModel extends IModel {
    /**
     * Return the state of the model.
     *
     * @return The state of the model.
     */
    ViewMode getState();

    /**
     * Set the state of the model.
     *
     * @param state The state of the model.
     */
    void setState(ViewMode state);

    /**
     * Return the category.
     *
     * @return The category.
     */
    Category getCategory();

    /**
     * Set the current category.
     *
     * @param category The current category.
     */
    void setCategory(Category category);
}