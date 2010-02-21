package org.jtheque.movies.services.impl;

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

import org.jtheque.core.managers.IManager;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.collection.IDaoCollections;
import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.utils.test.AbstractDBUnitTest;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.MovieConfiguration;
import org.jtheque.movies.MoviesModuleTest;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.services.able.IFilesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.services.impl.parsers.ToCharCategoryParser;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.core.managers.collection.CollectionImpl;
import org.jtheque.utils.bean.BeanUtils;
import org.jtheque.utils.unit.file.FileUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class FilesServiceTest extends AbstractDBUnitTest implements ApplicationContextAware {
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
    private IDaoCollections daoCollections;

    @Resource
    private DataSource dataSource;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public FilesServiceTest() {
        super("files.xml");
    }

    @Before
    public void setUpAll(){
        BeanUtils.set(moviesModule, "config", new MovieConfiguration());
        BeanUtils.set(Core.getInstance(), "application", new MoviesModuleTest.EmptyApplication());

        Map<Class<?>, IManager> managers = BeanUtils.getStatic(Managers.class, "MANAGERS");

        managers.put(IResourceManager.class, new FFMpegServiceTest.TestResourceManager(applicationContext));

        moviesModule.getConfig().setFFmpegLocation(System.getenv("FFMPEG_HOME"));

        FileUnit.initTestFileSystem();
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);

        PrimaryUtils.setPrimaryImpl("Movies");

        org.jtheque.core.managers.collection.Collection collection = new CollectionImpl();
        collection.setId(1);
        collection.setPassword("");
        collection.setProtection(false);
        collection.setTitle("Collection 1");

        daoCollections.setCurrentCollection(collection);
    }

    @After
    public void tearDown(){
        FileUnit.clearTestFileSystem();
    }

    @Test
    public void importMovies(){
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
    public void testGetMovieFiles(){
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