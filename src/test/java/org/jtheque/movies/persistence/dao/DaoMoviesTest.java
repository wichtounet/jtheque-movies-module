package org.jtheque.movies.persistence.dao;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jtheque.core.utils.test.AbstractDBUnitTest;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.primary.dao.able.IDaoCollections;
import org.jtheque.primary.od.able.Collection;
import org.jtheque.primary.od.impl.CollectionImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

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

/**
 * A test for DaoMovies.
 *
 * @author Baptiste Wicht
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class DaoMoviesTest extends AbstractDBUnitTest {
    @Resource
    private IDaoMovies daoMovies;

    @Resource
    private IDaoCollections daoCollections;

    @Resource
    private IDaoCategories daoCategories;

    @Resource
    private DataSource dataSource;

    static {
        Logger.getRootLogger().setLevel(Level.ERROR);
    }

    public DaoMoviesTest() {
        super("movies.xml");
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);

        PrimaryUtils.setPrimaryImpl("Movies");

        Collection collection = new CollectionImpl();
        collection.setId(1);
        collection.setPassword("");
        collection.setProtection(false);
        collection.setTitle("Collection 1");
        collection.setPrimaryImpl("Movies");

        daoCollections.setCurrentCollection(collection);
    }

    @Test
    public void initOK() {
        assertNotNull(daoMovies);
    }

    @Test
    public void getMovies() {
        assertEquals(5, daoMovies.getMovies().size());
    }

    @Test
    public void createEmptyMovie() {
        Movie movie = daoMovies.createMovie();

        assertNotNull(movie);
        assertEquals(0, movie.getId());
    }

    @Test
    public void getMovieById() {
        Movie movie = daoMovies.getMovie(2);

        assertNotNull(movie);
        assertEquals("Movie 2", movie.getTitle());
        assertEquals(1, movie.getTheCollection().getId());
    }

    @Test
    public void getMovieByTitle() {
        Movie movie = daoMovies.getMovie("Movie 1");

        assertNotNull(movie);
        assertEquals("Movie 1", movie.getTitle());
        assertEquals(1, movie.getId());

        Movie movie2 = daoMovies.getMovie("Movie asdf");

        assertNull(movie2);
    }

    @Test
    public void relations() {
        Movie movie = daoMovies.getMovie(2);

        assertEquals(2, movie.getCategories().size());

        for (Category c : movie.getCategories()) {
            assertTrue(c.getId() == 2 || c.getId() == 3);
        }
    }

    @Test
    public void createMovie() {
        Movie movie = daoMovies.createMovie();
        movie.setTitle("Created category");

        movie.addCategory(daoCategories.getCategory(1));

        daoMovies.create(movie);

        assertEquals(7, getRowCount("T_MOVIES"));
        assertEquals(9, getRowCount("T_MOVIES_CATEGORIES"));
    }

    @Test
    public void deleteMovie() {
        Movie movie = daoMovies.getMovie(1);
        daoMovies.delete(movie);

        assertEquals(5, getTable("T_MOVIES").getRowCount());
        assertNull(daoMovies.getMovie(1));

    }

    @Test
    public void saveMovie() {
        Movie movie = daoMovies.getMovie(1);
        movie.setTitle("New title");

        daoMovies.save(movie);

        assertEquals("New title", getValue("T_MOVIES", 0, "TITLE").toString());
    }

    @Test
    public void clearAll() {
        daoMovies.clearAll();

        assertEquals(0, getTable("T_MOVIES").getRowCount());
        assertEquals(0, daoMovies.getMovies().size());
    }
}