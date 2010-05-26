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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.jtheque.collections.impl.CollectionImpl;
import org.jtheque.collections.able.IDaoCollections;
import org.jtheque.core.able.ICore;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.MovieConfiguration;
import org.jtheque.movies.MoviesModuleTest;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.cleaners.ExtensionCleaner;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.primary.able.IPrimaryUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.BeanUtils;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.utils.ui.ImageUtils;
import org.jtheque.utils.unit.db.AbstractDBUnitTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    private IDaoCategories daoCategories;

    @Resource
    private IDaoCollections daoCollections;

    @Resource
    private IMoviesModule moviesModule;

    @Resource
    private IPrimaryUtils primaryUtils;

    @Resource
    private ICore core;

    @Resource
    private DataSource dataSource;

    private org.jtheque.collections.able.Collection collection;

    private String testFolder;

    static {
        ((Logger) LoggerFactory.getLogger("root")).setLevel(Level.ERROR);
    }

    public MoviesServiceTest() {
        super("movies.xml");
    }

    @PostConstruct
    public void init() {
        initDB(dataSource);

        primaryUtils.setPrimaryImpl("Movies");

        collection = new CollectionImpl();
        collection.setId(1);
        collection.setPassword("");
        collection.setProtection(false);
        collection.setTitle("Collection 1");

        daoCollections.setCurrentCollection(collection);

        BeanUtils.set(moviesModule, "config", new MovieConfiguration());
        BeanUtils.set(core, "application", new MoviesModuleTest.EmptyApplication());
        
        moviesModule.getConfig().setFFmpegLocation(System.getenv("FFMPEG_HOME"));
        testFolder = System.getenv("JTHEQUE_TESTS");
    }

    @Test
    public void initOK() {
        assertNotNull(moviesService);
    }

    @Test
    public void getEmptyMovie() {
        Movie emptyMovie = moviesService.getEmptyMovie();

        assertNotNull(emptyMovie);
        assertFalse(emptyMovie.isSaved());
        assertEquals("", emptyMovie.getTitle());
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

        Movie m4 = new MovieImpl();
        m4.setTitle("asdf");
        m4.setTheCollection(collection);
        movies.add(m4);

        moviesService.clean(movies, cleaners);

        for (Movie m : movies) {
            assertEquals("asdf", m.getTitle());
        }
    }

    @Test
    public void getMoviesOfCategory(){
        Collection<Movie> movies = moviesService.getMovies(daoCategories.getCategory("Category 4"), false);

        assertEquals(2, movies.size());

        String[] moviesCategory4 = {"Movie 3", "Movie 4"};

        for(Movie movie : movies){
            if(Arrays.binarySearch(moviesCategory4, movie.getTitle()) == -1){
                fail("Movie not in results");
            }
        }
    }

    @Test
    public void getMoviesOfLeafCategory(){
        Collection<Movie> movies = moviesService.getMovies(daoCategories.getCategory("Category 4"), true);

        assertEquals(2, movies.size());

        String[] moviesCategory4 = {"Movie 3", "Movie 4"};

        for(Movie movie : movies){
            if(Arrays.binarySearch(moviesCategory4, movie.getTitle()) == -1){
                fail("Movie not in results");
            }
        }
    }

    @Test
    public void getMoviesOfNotLeafCategory(){
        Collection<Movie> movies = moviesService.getMovies(daoCategories.getCategory("Category 5"), false);

        assertEquals(1, movies.size());

        String[] moviesCategory4 = {"Movie 2"};

        for(Movie movie : movies){
            if(Arrays.binarySearch(moviesCategory4, movie.getTitle()) == -1){
                fail("Movie not in results");
            }
        }
    }

    @Test
    public void getMoviesOfNotLeafCategory2(){
        Collection<Movie> movies = moviesService.getMovies(daoCategories.getCategory("Category 5"), true);

        assertEquals(3, movies.size());

        String[] moviesCategory4 = {"Movie 2", "Movie 3", "Movie 4"};

        for(Movie movie : movies){
            if(Arrays.binarySearch(moviesCategory4, movie.getTitle()) == -1){
                fail("Movie not in results");
            }
        }
    }
    
    @Test
    public void getDataType(){
        assertEquals("Movies", moviesService.getDataType());
    }

    @Test
    public void thumbnailIsNotUsed(){
        assertTrue(moviesService.thumbnailIsNotUsed("Test.jpg"));
        assertFalse(moviesService.thumbnailIsNotUsed("Movie 1.jpg"));
    }

    @Test
    public void getMoviesWithInvalidFiles(){
        Movie movie1 = moviesService.getMovie("Movie 1");
        Movie movie2 = moviesService.getMovie("Movie 2");

        movie1.setFile(FileUtils.getAnExistingFile().getAbsolutePath());
        movie2.setFile(FileUtils.getAnExistingFile().getAbsolutePath());

        Collection<? extends Movie> movies = moviesService.getMoviesWithInvalidFiles();
        String[] titles = {"Movie 3", "Movie 4", "Movie 5"};

        assertEquals(3, movies.size());

        for(Movie m : movies){
            if(Arrays.binarySearch(titles, m.getTitle()) == -1){
                fail("The returned movies are not the good");
            }
        }

        moviesService.getMovie("Movie 1").setFile(null);

        movies = moviesService.getMoviesWithInvalidFiles();

        assertEquals(4, movies.size());
    }

    @Test
    public void saveImage(){
        BufferedImage image = ImageUtils.openCompatibleImageFromFileSystem(testFolder + "test.jpg");

        Movie movie = moviesService.getMovie("Movie 1");

        moviesService.saveImage(movie, image);

        assertTrue(movie.getTitle().contains("Movie 1"));
        assertTrue(new File(moviesModule.getThumbnailFolderPath() + '/' + movie.getImage()).exists());
    }

    @Test
    public void fillInformations(){
        Movie movie1 = moviesService.getMovie("Movie 1");
        Movie movie2 = moviesService.getMovie("Movie 2");

        movie1.setFile(testFolder + "gok.avi");
        movie2.setFile(testFolder + "gok2.avi");

        Set<Movie> movies = new HashSet<Movie>(2);

        movies.add(movie1);
        movies.add(movie2);

        moviesService.fillInformations(movies, true, true, true);

        assertEquals("00:58:13.200", movie1.getDuration().toString());
        assertEquals("00:46:13.800", movie2.getDuration().toString());

        assertEquals("0640x0480", movie1.getResolution().toString());
        assertEquals("0640x0480", movie2.getResolution().toString());

        assertTrue(StringUtils.isNotEmpty(movie1.getImage()));
        assertTrue(StringUtils.isNotEmpty(movie2.getImage()));

        assertTrue(new File(moviesModule.getThumbnailFolderPath() + '/' + movie1.getImage()).exists());
        assertTrue(new File(moviesModule.getThumbnailFolderPath() + '/' + movie2.getImage()).exists());
    }
}