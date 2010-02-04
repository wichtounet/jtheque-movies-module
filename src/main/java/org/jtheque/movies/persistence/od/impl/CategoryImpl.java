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
import org.jtheque.primary.od.able.Collection;
import org.jtheque.primary.od.impl.abstraction.AbstractData;
import org.jtheque.primary.utils.TempUtils;

/**
 * A category implementation.
 *
 * @author Baptiste Wicht
 */
public final class CategoryImpl extends AbstractData implements Category {
    /**
     * The title of the category.
     */
    private String title;

    /**
     * The parent category.
     */
    private Category parent;

    /**
     * The collection.
     */
    private Collection theCollection;

    /**
     * The id of the parent, used only at cache loading to facilitate loading.
     */
    private int temporaryParent;

    /**
     * Construct a new empty CategoryImpl.
     */
    public CategoryImpl() {
        this("");
    }

    /**
     * Construct a new category using the given title.
     *
     * @param title The title of the category.
     */
    public CategoryImpl(String title) {
        super();

        this.title = title;
    }

    //Data methods

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Collection getTheCollection() {
        return theCollection;
    }

    @Override
    public void setTheCollection(Collection theCollection) {
        this.theCollection = theCollection;
    }

    @Override
    public Category getParent() {
        return parent;
    }

    @Override
    public void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public void setTemporaryParent(int temporaryParent) {
        this.temporaryParent = temporaryParent;
    }

    @Override
    public int getTemporaryParent() {
        return temporaryParent;
    }

    //Utility methods

    @Override
    public boolean isInCollection(Collection collection) {
        return theCollection != null && theCollection.equals(collection);
    }

    @Override
    public String getDisplayableText() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int hashCode() {
        return TempUtils.hashCodeDirect(getId(), title, theCollection);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        Category category = (Category) obj;

        return TempUtils.areEqualsDirect(this, category,
                getId(), title, theCollection,
                category.getId(), category.getTitle(), category.getTheCollection());
    }
}
