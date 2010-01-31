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

import org.apache.log4j.Logger;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.MoviesService;
import org.jtheque.utils.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.easymock.EasyMock.*;

/**
 * @author Baptiste Wicht
 */
public class MoviesServiceMockTest {
    private IMoviesService moviesService;

    private IDaoMovies daoMovies;

    @Before
    public void setUp() {
        moviesService = new MoviesService();

        daoMovies = createMock(IDaoMovies.class);

        try {
            Field field = MoviesService.class.getDeclaredField("daoMovies");

            field.setAccessible(true);

            field.set(moviesService, daoMovies);
        } catch (NoSuchFieldException e) {
            Logger.getLogger(getClass()).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Logger.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @Test
    public void testGetMovies() {
        expect(daoMovies.getMovies()).andReturn(CollectionUtils.<Movie>emptyList());

        replay(daoMovies);

        moviesService.getMovies();

        verify(daoMovies);
    }

    @Test
    public void testDelete() {
        expect(daoMovies.delete(new MovieImpl())).andReturn(true);

        replay(daoMovies);

        moviesService.delete(new MovieImpl());

        verify(daoMovies);
    }

    @Test
    public void testGetMovie() {
        expect(daoMovies.getMovie("asdf")).andReturn(null);

        replay(daoMovies);

        moviesService.getMovie("asdf");

        verify(daoMovies);
    }

    @Test
    public void testSave() {
        daoMovies.save(new MovieImpl());

        replay(daoMovies);

        moviesService.save(new MovieImpl());

        verify(daoMovies);
    }

    @Test
    public void testCreate() {
        daoMovies.create(new MovieImpl());

        replay(daoMovies);

        moviesService.create(new MovieImpl());

        verify(daoMovies);
    }

    @Test
    public void testGetDatas() {
        expect(daoMovies.getMovies()).andReturn(CollectionUtils.<Movie>emptyList());

        replay(daoMovies);

        moviesService.getDatas();

        verify(daoMovies);
    }

    @Test
    public void testClearAll() {
        daoMovies.clearAll();

        replay(daoMovies);

        moviesService.clearAll();

        verify(daoMovies);
    }
}
