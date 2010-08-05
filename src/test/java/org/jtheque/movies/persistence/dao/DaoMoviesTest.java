package org.jtheque.movies.persistence.dao;

import org.jtheque.collections.able.Collection;
import org.jtheque.collections.able.DaoCollections;
import org.jtheque.collections.impl.CollectionImpl;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.primary.able.IPrimaryUtils;
import org.jtheque.utils.unit.db.AbstractDBUnitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

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
    private DaoCollections daoCollections;

    @Resource
    private IDaoCategories daoCategories;

    @Resource
    private IPrimaryUtils primaryUtils;

    @Resource
    private DataSource dataSource;

    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);
    }

    public DaoMoviesTest() {
        super("movies.xml");
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);

        primaryUtils.setPrimaryImpl("Movies");

        Collection collection = new CollectionImpl();
        collection.setId(1);
        collection.setPassword("");
        collection.setProtection(false);
        collection.setTitle("Collection 1");

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
    public void getMoviesWithNoCollection() {
        daoCollections.setCurrentCollection(null);

        assertEquals(6, daoMovies.getMovies().size());
    }

    @Test
    public void createEmptyMovie() {
        Movie movie = daoMovies.create();

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
        Movie movie = daoMovies.create();
        movie.setTitle("Created category");

        movie.addCategory(daoCategories.getCategory(1));

        daoMovies.save(movie);

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