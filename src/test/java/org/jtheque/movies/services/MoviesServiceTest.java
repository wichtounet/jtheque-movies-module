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
import org.jtheque.core.managers.IManager;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.utils.test.AbstractDBUnitTest;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.MovieConfiguration;
import org.jtheque.movies.MoviesModule;
import org.jtheque.movies.MoviesModuleTest;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.FFMpegServiceTest;
import org.jtheque.movies.services.impl.cleaners.ExtensionCleaner;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.movies.utils.FileUtils;
import org.jtheque.primary.PrimaryUtils;
import org.jtheque.primary.dao.able.IDaoCollections;
import org.jtheque.primary.od.impl.CollectionImpl;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.ImageUtils;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
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
public class MoviesServiceTest extends AbstractDBUnitTest implements ApplicationContextAware {
    @Resource
    private IMoviesService moviesService;

    @Resource
    private IDaoCategories daoCategories;

    @Resource
    private IDaoCollections daoCollections;

    @Resource
    private IMoviesModule moviesModule;

    @Resource
    private DataSource dataSource;

    private org.jtheque.primary.od.able.Collection collection;

    private String testFolder;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

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
        
        try {
            Field f = MoviesModule.class.getDeclaredField("config");
            f.setAccessible(true);
            f.set(moviesModule, new MovieConfiguration());

            Field f2 = Core.class.getDeclaredField("application");
            f2.setAccessible(true);
            f2.set(Core.getInstance(), new MoviesModuleTest.EmptyApplication());

            Field f3 = Managers.class.getDeclaredField("MANAGERS");
            f3.setAccessible(true);

            Map<Class<?>, IManager> managers = (Map<Class<?>, IManager>) f3.get(null);
            managers.put(IResourceManager.class, new FFMpegServiceTest.TestResourceManager(applicationContext));
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        } catch (NoSuchFieldException e) {
            fail(e.getMessage());
        }
        
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