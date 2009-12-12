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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.properties.IPropertiesManager;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.abstraction.AbstractMovie;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.EqualsUtils;
import org.jtheque.utils.bean.HashCodeUtils;

import javax.swing.Icon;
import java.io.File;
import java.util.Collection;
import java.util.Date;

/**
 * A movie implementation.
 *
 * @author Baptiste Wicht
 */
public final class MovieImpl extends AbstractMovie {
    private Movie memento;
    private boolean mementoState;

    @Override
    public String getDisplayableText() {
        return getTitle();
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCode(this, "title", "categories", "file", "note", "theCollection");
    }

    @Override
    public Icon getIcon() {
        return Managers.getManager(IResourceManager.class).getIcon(IMoviesModule.IMAGES_BASE_NAME, "movie", ImageType.PNG);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if(EqualsUtils.areObjectIncompatible(this, obj)){
            return false;
        }

        final Movie other = (Movie) obj;
        
        if(EqualsUtils.areNotEquals(getTitle(), other.getTitle())){
            return false;
        }
        
        if(EqualsUtils.areNotEquals(getCategories(), other.getCategories())){
            return false;
        }
        
        if(EqualsUtils.areNotEquals(getFile(), other.getFile())){
            return false;
        }

        return !EqualsUtils.areNotEquals(getNote(), other.getNote());

    }

    @Override
    public void saveToMemento() {
        mementoState = true;

        memento = Managers.getManager(IPropertiesManager.class).createMemento(this);

        if (memento == null) {
            mementoState = false;
        }
    }

    @Override
    public void restoreMemento() {
        if (mementoState) {
            Managers.getManager(IPropertiesManager.class).restoreMemento(this, memento);
        }
    }

    @Override
    public void addCategories(Collection<Category> categories) {
        getCategories().addAll(categories);
    }

    @Override
    public void addCategory(Category category) {
        getCategories().add(category);
    }

    @Override
    public boolean isInCollection(org.jtheque.primary.od.able.Collection collection) {
        return getTheCollection() != null && getTheCollection().equals(collection);
    }

    @Override
    public boolean hasCategories() {
        return !getCategories().isEmpty();
    }

    @Override
    public boolean isOfCategory(Category category) {
        return category != null && getCategories().contains(category);
    }

    @Override
    public Date getFileLastModifiedDate() {
        if(StringUtils.isEmpty(getFile())){
            return null;
        }
        
        long lastModified = new File(getFile()).lastModified();
        
        return lastModified == 0L ? null : new Date(lastModified);
    }

    @Override
    public long getFileSize() {
        if(StringUtils.isEmpty(getFile())){
            return 0;
        }
        
        return new File(getFile()).length();
    }

    @Override
    public String toString() {
        return getDisplayableText();
    }
}