package org.jtheque.movies.persistence.od.impl;

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
import org.jtheque.movies.persistence.od.impl.abstraction.AbstractCategory;
import org.jtheque.utils.bean.HashCodeUtils;

/**
 * A category implementation.
 *
 * @author Baptiste Wicht
 */
public final class CategoryImpl extends AbstractCategory {
    /**
     * Construct a new empty CategoryImpl.
     */
    public CategoryImpl(){
        this("");
    }

    /**
     * Construct a new category using the given title.
     *
     * @param title The title of the category.
     */
    public CategoryImpl(String title){
        super();

        setTitle(title);
    }

    @Override
    public String getDisplayableText(){
        return getTitle();
    }

    @Override
    public String toString(){
        return getDisplayableText();
    }

    @Override
    public int hashCode(){
        return HashCodeUtils.hashCode(this, "title", "theCollection");
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }

        if (obj == null){
            return false;
        }

        if (getClass() != obj.getClass()){
            return false;
        }

        final Category other = (Category) obj;

        if (getTitle() == null){
            if (other.getTitle() != null){
                return false;
            }
        } else if (!getTitle().equals(other.getTitle())){
            return false;
        }

        return true;
    }
}
