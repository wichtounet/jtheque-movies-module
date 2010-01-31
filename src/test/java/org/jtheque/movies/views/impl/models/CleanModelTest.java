package org.jtheque.movies.views.impl.models;

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
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.views.able.models.ICleanModel;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class CleanModelTest {
    private ICleanModel model;

    @Before
    public void setUp(){
        model = new CleanModel();
    }

    @Test
    public void testGetCategory() throws Exception {
        Category category = new CategoryImpl("");

        assertNull(model.getCategory());

        model.setCategory(category);

        assertSame(category, model.getCategory());
    }

    @Test
    public void testGetMovie() throws Exception {
        Movie movie = new MovieImpl();

        assertNull(model.getMovie());

        model.setMovie(movie);

        assertSame(movie, model.getMovie());
    }

    @Test
    public void testIsMovieMode() throws Exception {
        assertFalse(model.isMovieMode());

        model.setMovie(new MovieImpl());

        assertTrue(model.isMovieMode());

        model.setCategory(new CategoryImpl(""));

        assertFalse(model.isMovieMode());
    }
}