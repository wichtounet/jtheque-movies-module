package org.jtheque.movies.views.impl.fb;

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

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.persistence.able.Note;

import java.util.Collection;
import java.util.HashSet;

/**
 * A form bean to keep data about movies.
 *
 * @author Baptiste Wicht
 */
public final class MovieFormBean implements IMovieFormBean {
    private String title;
    private Collection<Category> categories = new HashSet<Category>(6);
    private String file;
    private Note note;
    private PreciseDuration duration;
    private Resolution resolution;

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setCategories(Collection<Category> categories) {
        this.categories = new HashSet<Category>(categories);
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
    public void setDuration(PreciseDuration duration) {
        this.duration = duration;
    }

    @Override
    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    @Override
    public void fillMovie(Movie movie) {
        movie.setTitle(title);
        movie.setCategories(categories);
        movie.addCategories(categories);
        movie.setNote(note);
        movie.setFile(file);
        movie.setDuration(duration);
        movie.setResolution(resolution);
    }

    @Override
    public String toString() {
        return "MovieFormBean{" +
                "title='" + title + '\'' +
                ", categories=" + categories +
                ", file='" + file + '\'' +
                ", note=" + note.getValue() +
                ", duration=" + duration +
                ", resolution=" + resolution +
                '}';
    }
}