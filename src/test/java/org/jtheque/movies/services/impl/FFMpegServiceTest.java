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

import org.jtheque.core.able.ICore;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.MovieConfiguration;
import org.jtheque.movies.MoviesModuleTest;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.utils.bean.BeanUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class FFMpegServiceTest {
    @Resource
    private IFFMpegService ffmMpegService;

    @Resource
    private IMoviesModule moviesModule;

    @Resource
    private ICore core;

    private String testFolder;

    @Before
    public void setUp() {
        BeanUtils.set(moviesModule, "config", new MovieConfiguration());
        BeanUtils.set(core, "application", new MoviesModuleTest.EmptyApplication());

        moviesModule.getConfig().setFFmpegLocation(System.getenv("FFMPEG_HOME"));
        testFolder = System.getenv("JTHEQUE_TESTS");
    }

    @Test
    public void getResolution() {
        Resolution resolution = ffmMpegService.getResolution(new File(testFolder + "gok.avi"));

        assertEquals("0640x0480", resolution.toString());
    }

    @Test
    public void getDuration() {
        PreciseDuration duration = ffmMpegService.getDuration(new File(testFolder + "gok.avi"));

        assertEquals("00:58:13.200", duration.toString());
    }

    @Test
    public void generateRandomPreviewImage() {
        BufferedImage image = ffmMpegService.generateRandomPreviewImage(new File(testFolder + "gok.avi"));

        assertNotNull(image);

        assertEquals(200, image.getWidth());
    }

    @Test
    public void generatePreviewImage() {
        BufferedImage image = ffmMpegService.generatePreviewImage(new File(testFolder + "gok.avi"), "5");

        assertNotNull(image);

        assertEquals(200, image.getWidth());
    }

    @Test
    public void testGenerateImageFromUserInput() {
        BufferedImage image = ffmMpegService.generateImageFromUserInput(new File(testFolder + "test.jpg"));

        assertNotNull(image);

        assertEquals(200, image.getWidth());
    }
}