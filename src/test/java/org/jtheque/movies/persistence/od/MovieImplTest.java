package org.jtheque.movies.persistence.od;

import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.CategoryImpl;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.primary.od.able.Collection;
import org.jtheque.primary.od.impl.CollectionImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

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

public class MovieImplTest {
    private MovieImpl movie;

    @Before
    public void setUp(){
        movie = new MovieImpl();
    }

    @Test
    public void testAddCategories(){
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
    public void testAddCategory(){
        Category category = new CategoryImpl();

        movie.addCategory(category);

        assertEquals(1, movie.getCategories().size());
        assertTrue(movie.getCategories().contains(category));
    }

    @Test
    public void testIsInCollection(){
        Collection collection1 = new CollectionImpl();
        collection1.setId(10);

        Collection collection2 = new CollectionImpl();
        collection2.setId(12);

        assertFalse(movie.isInCollection(collection1));
        assertFalse(movie.isInCollection(collection2));

        movie.setTheCollection(collection1);

        assertFalse(movie.isInCollection(collection2));
        assertTrue(movie.isInCollection(collection1));
    }

    @Test
    public void testHasCategories(){
        assertFalse(movie.hasCategories());

        Category category = new CategoryImpl();

        movie.addCategory(category);

        assertTrue(movie.hasCategories());
    }

    @Test
    public void testHashCode(){
        movie.setId(123);
        movie.setTitle("Test");
        movie.setFile("C:\\test.dat");
        movie.setNote(DaoNotes.getInstance().getNote(DaoNotes.NoteType.BAD));

        Movie movie2 = new MovieImpl();
        movie2.setId(123);
        movie2.setTitle("Test");
        movie2.setFile("C:\\test.dat");
        movie2.setNote(DaoNotes.getInstance().getNote(DaoNotes.NoteType.BAD));

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
    public void equals(){
        movie.setId(123);
        movie.setTitle("Test");
        movie.setFile("C:\\test.dat");
        movie.setNote(DaoNotes.getInstance().getNote(DaoNotes.NoteType.BAD));

        Movie movie2 = new MovieImpl();
        movie2.setId(123);
        movie2.setTitle("Test");
        movie2.setFile("C:\\test.dat");
        movie2.setNote(DaoNotes.getInstance().getNote(DaoNotes.NoteType.BAD));

        Category cat1 = new CategoryImpl("Test 1");
        cat1.setId(12);

        Category cat2 = new CategoryImpl("Test 1");
        cat2.setId(12);

        movie.addCategory(cat1);
        movie.addCategory(cat2);

        movie2.addCategory(cat1);
        movie2.addCategory(cat2);

        assertTrue(movie.equals(movie2));
        assertTrue(movie2.equals(movie));

        movie2.getCategories().clear();

        assertFalse(movie.equals(movie2));
        assertFalse(movie2.equals(movie));

        movie2.addCategory(cat1);
        movie2.addCategory(cat2);

        movie.setTitle("Test 1234");


        assertFalse(movie.equals(movie2));
        assertFalse(movie2.equals(movie));
    }
}