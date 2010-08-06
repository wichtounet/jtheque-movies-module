package org.jtheque.movies.persistence.od;

import org.jtheque.collections.able.DataCollection;
import org.jtheque.collections.impl.DataCollectionImpl;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.persistence.able.DaoNotes;
import org.jtheque.persistence.able.Note;
import org.jtheque.utils.io.FileUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class MovieImplTest {
    private MovieImpl movie;

    @Resource
    private DaoNotes daoNotes;

    @Before
    public void setUp() {
        movie = new MovieImpl();
    }

    @Test
    public void testAddCategories() {
        Category category1 = new CategoryImpl();
        category1.setId(1);

        Category category2 = new CategoryImpl();
        category2.setId(2);

        movie.addCategories(Arrays.asList(category1, category2));

        assertEquals(2, movie.getCategories().size());
        assertTrue(movie.getCategories().contains(category1));
        assertTrue(movie.getCategories().contains(category2));
    }

    @Test
    public void testAddCategory() {
        Category category = new CategoryImpl();

        movie.addCategory(category);

        assertEquals(1, movie.getCategories().size());
        assertTrue(movie.getCategories().contains(category));
    }

    @Test
    public void testIsInCollection() {
        DataCollection collection1 = new DataCollectionImpl();
        collection1.setId(10);

        DataCollection collection2 = new DataCollectionImpl();
        collection2.setId(12);

        assertFalse(movie.isInCollection(collection1));
        assertFalse(movie.isInCollection(collection2));

        movie.setTheCollection(collection1);

        assertFalse(movie.isInCollection(collection2));
        assertTrue(movie.isInCollection(collection1));
    }

    @Test
    public void testHasCategories() {
        assertFalse(movie.hasCategories());

        Category category = new CategoryImpl();

        movie.addCategory(category);

        assertTrue(movie.hasCategories());
    }

    @Test
    public void testToString() {
        movie.setTitle("Super title");

        assertEquals("Super title", movie.toString());

        movie.setTitle("Super title 2");

        assertEquals("Super title 2", movie.toString());
    }

    @Test
    public void testHashCode() {
        movie.setId(123);
        movie.setTitle("Test");
        movie.setFile("C:\\test.dat");
        movie.setNote(Note.BAD);

        Movie movie2 = new MovieImpl();
        movie2.setId(123);
        movie2.setTitle("Test");
        movie2.setFile("C:\\test.dat");
        movie2.setNote(Note.BAD);

        Category cat1 = new CategoryImpl("Test 1");
        cat1.setId(12);

        Category cat2 = new CategoryImpl("Test 2");
        cat2.setId(122);

        movie.addCategory(cat1);
        movie.addCategory(cat2);

        movie2.addCategory(cat1);
        movie2.addCategory(cat2);

        assertEquals(movie.hashCode(), movie2.hashCode());

        movie2.getCategories().clear();

        assertFalse(movie.hashCode() == movie2.hashCode());

        movie2.addCategory(cat1);
        movie2.addCategory(cat2);

        movie.setTitle("Test 1234");

        assertFalse(movie.hashCode() == movie2.hashCode());
    }

    @Test
    public void testIsOfCategory() {
        Category cat1 = new CategoryImpl("Test 1");
        cat1.setId(12);

        Category cat2 = new CategoryImpl("Test 2");
        cat2.setId(122);

        assertFalse(movie.isOfCategory(null));
        assertFalse(movie.isOfCategory(cat1));
        assertFalse(movie.isOfCategory(cat2));

        movie.addCategory(cat1);

        assertTrue(movie.isOfCategory(cat1));
        assertFalse(movie.isOfCategory(null));
    }

    @Test
    public void equals() {
        movie.setId(123);
        movie.setTitle("Test");
        movie.setFile("C:\\test.dat");
        movie.setNote(Note.BAD);

        Movie movie2 = new MovieImpl();
        movie2.setId(123);
        movie2.setTitle("Test");
        movie2.setFile("C:\\test.dat");
        movie2.setNote(Note.BAD);

        Category cat1 = new CategoryImpl("Test 1");
        cat1.setId(12);

        Category cat2 = new CategoryImpl("Test 1");
        cat2.setId(12);

        movie.addCategory(cat1);
        movie.addCategory(cat2);

        movie2.addCategory(cat1);
        movie2.addCategory(cat2);

        assertFalse(movie.equals(null));
        assertFalse(movie2.equals(null));

        assertFalse(movie.equals(new CategoryImpl()));

        assertEquals(movie, movie2);
        assertEquals(movie2, movie);

        movie2.getCategories().clear();

        assertFalse(movie.equals(movie2));
        assertFalse(movie2.equals(movie));

        movie2.addCategory(cat1);
        movie2.addCategory(cat2);

        movie.setTitle("Test 1234");


        assertFalse(movie.equals(movie2));
        assertFalse(movie2.equals(movie));
    }

    @Test
    public void memento() {
        movie.setTitle("Title before");
        movie.setFile("File before");
        movie.setResolution(new Resolution("450x600"));

        movie.saveToMemento();

        movie.setTitle("Title after");
        movie.setFile("File after");
        movie.setResolution(new Resolution("600x450"));

        movie.restoreMemento();

        assertEquals("Title before", movie.getTitle());
        assertEquals("File before", movie.getFile());
        assertEquals("0450x0600", movie.getResolution().toString());
    }

    @Test
    public void getFileSize() {
        assertEquals(0, movie.getFileSize());

        File f = FileUtils.getAnExistingFile();

        movie.setFile(f.getAbsolutePath());

        assertEquals(f.length(), movie.getFileSize());
    }

    @Test
    public void getLastModifiedData() {
        assertNull(movie.getFileLastModifiedDate());

        File f = FileUtils.getAnExistingFile();

        movie.setFile(f.getAbsolutePath());

        assertEquals(f.lastModified(), movie.getFileLastModifiedDate().getTime());
    }
}