package org.jtheque.movies.services;

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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jtheque.core.utils.test.AbstractDBUnitTest;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.cleaners.ExtensionCleaner;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.primary.dao.able.IDaoCollections;
import org.jtheque.primary.od.impl.CollectionImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Baptiste Wicht
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class MoviesServiceTest extends AbstractDBUnitTest {
    @Resource
    private IMoviesService moviesService;

    @Resource
    private IDaoCollections daoCollections;

    @Resource
    private DataSource dataSource;

    private org.jtheque.primary.od.able.Collection collection;

    static {
        Logger.getRootLogger().setLevel(Level.ERROR);
    }

    public MoviesServiceTest() {
        super("movies.xml");
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);

        PrimaryUtils.setPrimaryImpl("Movies");

        collection = new CollectionImpl();
        collection.setId(1);
        collection.setPassword("");
        collection.setProtection(false);
        collection.setTitle("Collection 1");
        collection.setPrimaryImpl("Movies");

        daoCollections.setCurrentCollection(collection);
    }

    @Test
    public void initOK() {
        assertNotNull(moviesService);
    }

    @Test
    public void fileExists() {
        assertFalse(moviesService.fileExists("C:\\test.avi"));
        assertTrue(moviesService.fileExists("C:\\movies\\movie1.avi"));
    }

    @Test
    public void fileExistsInOtherMovie() {
        assertFalse(moviesService.fileExistsInOtherMovie(moviesService.getMovie("Movie 1"), "C:\\test.avi"));
        assertFalse(moviesService.fileExistsInOtherMovie(moviesService.getMovie("Movie 1"), "C:\\movies\\movie1.avi"));
        assertTrue(moviesService.fileExistsInOtherMovie(moviesService.getMovie("Movie 2"), "C:\\movies\\movie1.avi"));
    }

    @Test
    public void testCleanOne() {
        Collection<NameCleaner> cleaners = new ArrayList<NameCleaner>(1);

        cleaners.add(new ExtensionCleaner());

        Movie m1 = new MovieImpl();
        m1.setTitle(" asdf.txt");
        m1.setTheCollection(collection);

        moviesService.clean(m1, cleaners);
    }

    @Test
    public void testCleanCollection() {
        Collection<NameCleaner> cleaners = new ArrayList<NameCleaner>(1);

        cleaners.add(new ExtensionCleaner());

        Collection<Movie> movies = new ArrayList<Movie>(3);

        Movie m1 = new MovieImpl();
        m1.setTitle(" asdf.txt");
        m1.setTheCollection(collection);
        movies.add(m1);

        Movie m2 = new MovieImpl();
        m2.setTitle(" asdf.wba ");
        m2.setTheCollection(collection);
        movies.add(m2);

        Movie m3 = new MovieImpl();
        m3.setTitle(" asdf    ");
        m3.setTheCollection(collection);
        movies.add(m3);

        moviesService.clean(movies, cleaners);

        for (Movie m : movies) {
            assertEquals("asdf", m.getTitle());
        }
    }
}