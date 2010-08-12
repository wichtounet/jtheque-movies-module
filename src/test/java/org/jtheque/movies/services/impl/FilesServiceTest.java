package org.jtheque.movies.services.impl;

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

import org.jtheque.collections.DaoCollections;
import org.jtheque.collections.DataCollection;
import org.jtheque.collections.impl.DataCollectionImpl;
import org.jtheque.core.Core;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.MovieConfiguration;
import org.jtheque.movies.MoviesModuleTest;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.services.able.IFilesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.services.impl.parsers.ToCharCategoryParser;
import org.jtheque.primary.able.IPrimaryUtils;
import org.jtheque.utils.bean.BeanUtils;
import org.jtheque.utils.unit.db.AbstractDBUnitTest;
import org.jtheque.utils.unit.file.FileUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class FilesServiceTest extends AbstractDBUnitTest {
    @Resource
    private IFilesService filesService;

    @Resource
    private IFFMpegService ffMpegService;

    @Resource
    private IMoviesService moviesService;

    @Resource
    private IDaoMovies daoMovies;

    @Resource
    private IMoviesModule moviesModule;

    @Resource
    private DaoCollections daoCollections;

    @Resource
    private DataSource dataSource;

    @Resource
    private IPrimaryUtils primaryUtils;

    @Resource
    private Core core;

    public FilesServiceTest() {
        super("files.xml");
    }

    @Before
    public void setUpAll() {
        BeanUtils.set(moviesModule, "config", new MovieConfiguration());
        BeanUtils.set(core, "application", new MoviesModuleTest.EmptyApplication());

        moviesModule.getConfig().setFFmpegLocation(System.getenv("FFMPEG_HOME"));

        FileUnit.initTestFileSystem();
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);

        primaryUtils.setPrimaryImpl("Movies");

        DataCollection collection = new DataCollectionImpl();
        collection.setId(1);
        collection.setPassword("");
        collection.setProtection(false);
        collection.setTitle("Collection 1");

        daoCollections.setCurrentCollection(collection);
    }

    @After
    public void tearDown() {
        FileUnit.clearTestFileSystem();
    }

    @Test
    public void importMovies() {
        Collection<File> files = new ArrayList<File>(5);
        Collection<FileParser> parsers = new ArrayList<FileParser>(1);

        files.add(FileUnit.getFile("Cat 1-Movie 1.avi"));
        files.add(FileUnit.getFile("Cat 1-Movie 2.avi"));
        files.add(FileUnit.getFile("Cat 2-Movie 3.avi"));
        files.add(FileUnit.getFile("Cat 2-Movie 4.avi"));
        files.add(FileUnit.getFile("Cat 2-Movie 5.avi"));

        parsers.add(new ToCharCategoryParser("-"));

        filesService.importMovies(files, parsers);

        assertNotNull(moviesService.getMovie("Movie 1.avi"));
        assertNotNull(moviesService.getMovie("Movie 2.avi"));
        assertNotNull(moviesService.getMovie("Movie 3.avi"));
        assertNotNull(moviesService.getMovie("Movie 4.avi"));
        assertNotNull(moviesService.getMovie("Movie 5.avi"));
    }

    @Test
    public void testGetMovieFiles() {
        FileUnit.addFile("Movie 1.avi");
        FileUnit.addFile("Movie 2.avi");
        FileUnit.addFile("Test 2.jpg");
        FileUnit.addFolder("Folder 1");
        FileUnit.addFile("Folder 1", "Movie 3.avi");
        FileUnit.addFile("Folder 1", "Test 2.jpg");
        FileUnit.addFolder("Folder 2");
        FileUnit.addFile("Folder 2", "Movie 4.avi");
        FileUnit.addFile("Folder 2", "Test 2.jpg");
        FileUnit.addFile("Folder 2", "Movie 5.avi");

        Collection<File> files = filesService.getMovieFiles(FileUnit.getFile("Movie 1.avi"));

        assertTrue(files.isEmpty());

        files = filesService.getMovieFiles(FileUnit.getFile("Folder 1"));

        assertEquals(1, files.size());

        assertTrue(files.contains(FileUnit.getFile("Folder 1", "Movie 3.avi")));

        files = filesService.getMovieFiles(FileUnit.getFile("Folder 2"));

        assertEquals(2, files.size());

        assertTrue(files.contains(FileUnit.getFile("Folder 2", "Movie 4.avi")));
        assertTrue(files.contains(FileUnit.getFile("Folder 2", "Movie 5.avi")));

        files = filesService.getMovieFiles(new File(FileUnit.getRootFolder()));

        assertEquals(5, files.size());

        assertTrue(files.contains(FileUnit.getFile("Movie 1.avi")));
        assertTrue(files.contains(FileUnit.getFile("Movie 2.avi")));
        assertTrue(files.contains(FileUnit.getFile("Folder 1", "Movie 3.avi")));
        assertTrue(files.contains(FileUnit.getFile("Folder 2", "Movie 4.avi")));
        assertTrue(files.contains(FileUnit.getFile("Folder 2", "Movie 5.avi")));
    }
}