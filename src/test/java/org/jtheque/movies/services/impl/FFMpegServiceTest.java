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
import org.jtheque.core.managers.ManagerException;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.MovieConfiguration;
import org.jtheque.movies.MoviesModule;
import org.jtheque.movies.MoviesModuleTest;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class FFMpegServiceTest implements ApplicationContextAware{
    @Resource
    private IFFMpegService ffmMpegService;

    @Resource
    private IMoviesModule moviesModule;

    private String testFolder;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Before
    public void setUp(){
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
            managers.put(IResourceManager.class, new TestResourceManager(applicationContext));
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        } catch (NoSuchFieldException e) {
            fail(e.getMessage());
        }

        moviesModule.getConfig().setFFmpegLocation(System.getenv("FFMPEG_HOME"));
        testFolder = System.getenv("JTHEQUE_TESTS");
    }

    @Test
    public void getResolution(){
        Resolution resolution = ffmMpegService.getResolution(new File(testFolder + "gok.avi"));

        assertEquals("0640x0480", resolution.toString());
    }

    @Test
    public void getDuration(){
        PreciseDuration duration = ffmMpegService.getDuration(new File(testFolder + "gok.avi"));

        assertEquals("00:58:13.200", duration.toString());
    }

    @Test
    public void generateRandomPreviewImage(){
        BufferedImage image = ffmMpegService.generateRandomPreviewImage(new File(testFolder + "gok.avi"));

        assertNotNull(image);

        assertEquals(200, image.getWidth());
    }

    @Test
    public void generatePreviewImage(){
        System.out.println(testFolder + "gok.avi");

        BufferedImage image = ffmMpegService.generatePreviewImage(new File(testFolder + "gok.avi"), "5");

        assertNotNull(image);

        assertEquals(200, image.getWidth());
    }

    @Test
    public void testGenerateImageFromUserInput(){
        System.out.println(testFolder + "test.jpg");

        BufferedImage image = ffmMpegService.generateImageFromUserInput(new File(testFolder + "test.jpg"));

        assertNotNull(image);

        assertEquals(200, image.getWidth());
    }

    public static final class TestResourceManager implements IResourceManager, IManager {
        private final ApplicationContext applicationContext;

        public TestResourceManager(ApplicationContext applicationContext) {
            super();


            this.applicationContext = applicationContext;
        }

        @Override
        public org.springframework.core.io.Resource getResource(String path) {
            return applicationContext.getResource(path);
        }

        @Override
        public InputStream getResourceAsStream(String path) {
            InputStream stream = null;

            try {
                stream = getResource(path).getInputStream();
            } catch (IOException e) {
                LoggerFactory.getLogger(getClass()).error("Unable to load stream for {}", path);
                LoggerFactory.getLogger(getClass()).error(e.getMessage());
            }

            return stream;
        }

        @Override
        public ImageIcon getIcon(String baseName, String id, ImageType type) {
            return null;
        }

        @Override
        public ImageIcon getIcon(String id, ImageType type) {
            return null;
        }

        @Override
        public ImageIcon getIcon(String id) {
            return null;
        }

        @Override
        public BufferedImage getImage(String baseName, String id, ImageType type, int width) {
            return null;
        }

        @Override
        public BufferedImage getImage(String baseName, String id, ImageType type) {
            return null;
        }

        @Override
        public BufferedImage getImage(String id, ImageType type, int width) {
            return null;
        }

        @Override
        public BufferedImage getImage(String id, ImageType type) {
            return null;
        }

        @Override
        public BufferedImage getImage(String id, int width) {
            return null;
        }

        @Override
        public BufferedImage getImage(String id) {
            return null;
        }

        @Override
        public Color getColor(String name) {
            return null;
        }

        @Override
        public Action getAction(String name) {
            return null;
        }

        @Override
        public void preInit() {

        }

        @Override
        public void init() throws ManagerException {

        }

        @Override
        public void close() {

        }
    }
}