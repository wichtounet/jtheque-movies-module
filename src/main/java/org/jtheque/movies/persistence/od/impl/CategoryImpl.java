package org.jtheque.movies.persistence.od.impl;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.collections.DataCollection;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.primary.utils.AbstractData;
import org.jtheque.utils.bean.EqualsUtils;
import org.jtheque.utils.bean.HashCodeUtils;

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
    private DataCollection theCollection;

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
    public DataCollection getTheCollection() {
        return theCollection;
    }

    @Override
    public void setTheCollection(DataCollection theCollection) {
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
    public boolean isInCollection(DataCollection collection) {
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
        return HashCodeUtils.hashCodeDirect(getId(), title, theCollection);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        Category category = (Category) obj;

        return EqualsUtils.areEqualsDirect(this, category,
                getId(), title, theCollection,
                category.getId(), category.getTitle(), category.getTheCollection());
    }
}
