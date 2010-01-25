package org.jtheque.movies.persistence.od.able;

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

import org.jtheque.primary.od.able.Collection;
import org.jtheque.primary.od.able.Data;

/**
 * @author Baptiste Wicht
 */
public interface Category extends Data {
	String NAME = "category.name";
	int NAME_LENGTH = 100;

    /**
     * Return the title of the category.
     *
     * @return The title of the category.
     */
    String getTitle();

    /**
     * Set the title of the category.
     *
     * @param title The title of the category.
     */
    void setTitle(String title);

    /**
     * Return the collection.
     *
     * @return The collection.
     */
    Collection getTheCollection();

    /**
     * Set the collection of the category.
     *
     * @param theCollection The collection of the category.
     */
    void setTheCollection(Collection theCollection);
}
