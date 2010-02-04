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

import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.core.utils.db.Note;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

public class MovieFormBeanTest {
    @Test
    public void fillMovie(){
        MovieFormBean fb = new MovieFormBean();

        PreciseDuration duration = new PreciseDuration(22L);
        Resolution resolution = new Resolution("450x220");
        Note note = DaoNotes.getInstance().getDefaultNote();

        Collection<Category> categories = new HashSet<Category>(2);

        categories.add(new CategoryImpl("Cat 1"));
        categories.add(new CategoryImpl("Cat 2"));

        fb.setTitle("Super title");
        fb.setFile("Super file");
        fb.setDuration(duration);
        fb.setResolution(resolution);
        fb.setNote(note);
        fb.setCategories(categories);

        Movie movie = new MovieImpl();

        fb.fillMovie(movie);

        assertEquals("Super title", movie.getTitle());
        assertEquals("Super file", movie.getFile());
        assertSame(note, movie.getNote());
        assertSame(duration, movie.getDuration());
        assertSame(resolution, movie.getResolution());
        assertEquals(categories, movie.getCategories());
    }
}
