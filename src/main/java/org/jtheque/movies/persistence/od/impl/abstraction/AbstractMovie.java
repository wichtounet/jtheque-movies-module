package org.jtheque.movies.persistence.od.impl.abstraction;

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

import org.jtheque.core.utils.db.Note;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.primary.od.able.Collection;
import org.jtheque.primary.od.impl.abstraction.AbstractData;

import java.util.HashSet;
import java.util.Set;

/**
 * A movie.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractMovie extends AbstractData implements Movie {
    private String title;
    private final Set<Category> categories = new HashSet<Category>(6);
    private String file;
    private Note note;
    private Collection theCollection;

    @Override
    public final Collection getTheCollection() {
        return theCollection;
    }

    @Override
    public final void setTheCollection(Collection theCollection) {
        this.theCollection = theCollection;
    }

    @Override
    public final String getTitle() {
        return title;
    }

    @Override
    public final void setTitle(String title) {
        this.title = title;
    }

    @Override
    public final Set<Category> getCategories() {
        return categories;
    }

    @Override
    public final String getFile() {
        return file;
    }

    @Override
    public final void setFile(String file) {
        this.file = file;
    }

    @Override
    public final Note getNote() {
        return note;
    }

    @Override
    public final void setNote(Note note) {
        this.note = note;
    }
}