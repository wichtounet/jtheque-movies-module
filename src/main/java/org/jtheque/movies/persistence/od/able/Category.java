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

/**
 * A category specification
 *
 * @author Baptiste Wicht
 */
public interface Category extends CollectionData {
    String NAME = "category.name";
    String PARENT = "category.parent";

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
     * Return the parent category.
     *
     * @return the parent Category or null if there is no parent.
     */
    Category getParent();

    /**
     * Set the parent category.
     *
     * @param parent The parent category.
     */
    void setParent(Category parent);

    /**
     * Set the temporary parent. This method is used when we cannot get the category
     * referenced by the specified id. This method must not be used once the cache of categories
     * is loaded.
     *
     * @param parentId The id of the parent category.
     */
    void setTemporaryParent(int parentId);

    /**
     * Return the id of the temporary parent category.
     *
     * @return The id of the parent category.
     * @see #setTemporaryParent(int)
     */
	int getTemporaryParent();
}
