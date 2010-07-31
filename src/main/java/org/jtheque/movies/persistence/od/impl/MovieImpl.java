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

import org.jtheque.images.able.ImageService;
import org.jtheque.movies.MoviesResources;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.persistence.able.Note;
import org.jtheque.primary.utils.AbstractData;
import org.jtheque.utils.bean.BeanUtils;
import org.jtheque.utils.bean.EqualsUtils;
import org.jtheque.utils.bean.HashCodeUtils;
import org.jtheque.utils.io.FileUtils;

import javax.swing.Icon;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A movie implementation.
 *
 * @author Baptiste Wicht
 */
public final class MovieImpl extends AbstractData implements Movie {
    private Movie memento;
    private boolean mementoState;

    private String title;
    private String image;
    private Set<Category> categories = new HashSet<Category>(6);
    private String file;
    private Note note;
    private org.jtheque.collections.able.Collection theCollection;
    private PreciseDuration duration;
    private Resolution resolution;

    private static final String[] FIELDS = {"id", "title", "categories", "file", "note", "theCollection", "duration", "resolution", "image"};

    //Data methods

    @Override
    public org.jtheque.collections.able.Collection getTheCollection() {
        return theCollection;
    }

    @Override
    public void setTheCollection(org.jtheque.collections.able.Collection theCollection) {
        this.theCollection = theCollection;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public void setCategories(Collection<Category> categories) {
        this.categories = new HashSet<Category>(categories);
    }

    @Override
    public String getFile() {
        return file;
    }

    @Override
    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public PreciseDuration getDuration() {
        return duration;
    }

    @Override
    public void setDuration(PreciseDuration duration) {
        this.duration = duration;
    }

    @Override
    public Resolution getResolution() {
        return resolution;
    }

    @Override
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    //Utility methods

    @Override
    public String getDisplayableText() {
        return title;
    }

    @Override
    public Icon getIcon(ImageService imageService) {
        return imageService.getIcon(MoviesResources.MOVIE_ICON);
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCodeDirect(title, categories, file, note, theCollection, duration, resolution, image);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Movie)) {
            return false;
        }

        Movie movie = (Movie) obj;

        return EqualsUtils.areEqualsDirect(this, movie,
                title, categories, file, note, theCollection, duration, resolution, image,
                movie.getTitle(), movie.getCategories(), movie.getFile(), movie.getNote(), movie.getTheCollection(),
                movie.getDuration(), movie.getResolution(), movie.getImage());
    }

    @Override
    public void saveToMemento() {
        mementoState = true;

        memento = BeanUtils.createQuickMemento(this, FIELDS);
    }

    @Override
    public void restoreMemento() {
        if (mementoState) {
            BeanUtils.restoreQuickMemento(this, memento, FIELDS);
        }
    }

    @Override
    public void addCategories(Collection<Category> categories) {
        this.categories.addAll(categories);
    }

    @Override
    public void addCategory(Category category) {
        categories.add(category);
    }

    @Override
    public boolean isInCollection(org.jtheque.collections.able.Collection collection) {
        return theCollection != null && theCollection.equals(collection);
    }

    @Override
    public boolean hasCategories() {
        return !categories.isEmpty();
    }

    @Override
    public boolean isOfCategory(Category category) {
        return category != null && categories.contains(category);
    }

    @Override
    public Date getFileLastModifiedDate() {
        return FileUtils.getLastModifiedDate(file);
    }

    @Override
    public long getFileSize() {
        return FileUtils.getFileSize(file);
    }

    @Override
    public String toString() {
        return title;
    }
}