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

import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.MoviesService;
import org.jtheque.movies.services.impl.cleaners.ExtensionCleaner;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.utils.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Baptiste Wicht
 */
public class MoviesServiceTest {
    private IMoviesService moviesService;

    private IDaoMovies daoMovies;

    @Before
    public void setUp(){
        moviesService = new MoviesService();

        daoMovies = createMock(IDaoMovies.class);

        try {
            Field field = MoviesService.class.getDeclaredField("daoMovies");

            field.setAccessible(true);

            field.set(moviesService, daoMovies);
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMovies(){
        expect(daoMovies.getMovies()).andReturn(CollectionUtils.<Movie>emptyList());

        replay(daoMovies);

        moviesService.getMovies();

        verify(daoMovies);
    }

    @Test
    public void testDelete(){
        expect(daoMovies.delete(new MovieImpl())).andReturn(true);

        replay(daoMovies);

        moviesService.delete(new MovieImpl());

        verify(daoMovies);
    }

    @Test
    public void testSave(){
        daoMovies.save(new MovieImpl());

        replay(daoMovies);

        moviesService.save(new MovieImpl());

        verify(daoMovies);
    }

    @Test
    public void testCreate(){
        daoMovies.create(new MovieImpl());

        replay(daoMovies);

        moviesService.create(new MovieImpl());

        verify(daoMovies);
    }

    @Test
    public void testClean(){
        Collection<NameCleaner> cleaners = new ArrayList<NameCleaner>(1);

        cleaners.add(new ExtensionCleaner());

        Collection<Movie> movies = new ArrayList<Movie>(3);

        MovieImpl m1 = new MovieImpl();
        m1.setTitle(" asdf.txt");
        movies.add(m1);

        MovieImpl m2 = new MovieImpl();
        m2.setTitle(" asdf.wba ");
        movies.add(m2);

        MovieImpl m3 = new MovieImpl();
        m3.setTitle(" asdf    ");
        movies.add(m3);

        moviesService.clean(movies, cleaners);

        for (Movie m : movies){
            assertEquals("asdf", m.getTitle());
        }
    }

    @Test
    public void testGetDatas(){
        expect(daoMovies.getMovies()).andReturn(CollectionUtils.<Movie>emptyList());

        replay(daoMovies);

        moviesService.getDatas();

        verify(daoMovies);
    }

    @Test
    public void testClearAll(){
        daoMovies.clearAll();

        replay(daoMovies);

        moviesService.clearAll();

        verify(daoMovies);
    }
}
