package org.jtheque.movies.services;

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

import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.MoviesService;
import org.jtheque.persistence.able.DataListener;
import org.jtheque.utils.collections.CollectionUtils;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

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
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
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

    @Test
    public void testAddDataListener() {
        final DataListener listener = new DataListener() {
            @Override
            public void dataChanged() {
            }
        };

        daoMovies.addDataListener(listener);

        replay(daoMovies);

        moviesService.addDataListener(listener);

        verify(daoMovies);
    }
}
