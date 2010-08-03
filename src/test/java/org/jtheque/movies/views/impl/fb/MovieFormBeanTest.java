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
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.persistence.able.DaoNotes;
import org.jtheque.persistence.able.Note;
import org.jtheque.utils.StringUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class MovieFormBeanTest {
    @Resource
    private DaoNotes daoNotes;

    @Test
    public void fillMovie() {
        IMovieFormBean fb = new MovieFormBean();

        PreciseDuration duration = new PreciseDuration(22L);
        Resolution resolution = new Resolution("450x220");
        Note note = daoNotes.getNote(org.jtheque.persistence.impl.DaoNotes.NoteType.UNDEFINED);

        Collection<Category> categories = new HashSet<Category>(2);

        categories.add(new CategoryImpl("Cat 1"));
        categories.add(new CategoryImpl("Cat 2"));

        fb.setTitle("Super title");
        fb.setFile("Super file");
        fb.setDuration(duration);
        fb.setResolution(resolution);
        fb.setNote(note);
        fb.setCategories(categories);

        assertTrue(StringUtils.isNotEmpty(fb.toString()));

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
