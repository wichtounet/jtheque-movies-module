package org.jtheque.movies.views.impl.fb;

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

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public final class MovieFormBean implements IMovieFormBean {
    private String title;
    private Collection<Category> categories = new ArrayList<Category>(6);
    private String file;
    private Note note;

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setCategories(Collection<Category> categories) {
        this.categories = new ArrayList<Category>(categories);
    }

    @Override
    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public void fillMovie(Movie movie) {
        movie.setTitle(title);
        movie.addCategories(categories);
        movie.setNote(note);
        movie.setFile(file);
    }

    @Override
    public String toString() {
        return "MovieFormBean{" +
                "title='" + title + '\'' +
                ", categories=" + categories +
                ", file='" + file + '\'' +
                ", note=" + note +
                '}';
    }
}