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

import org.jtheque.errors.able.IErrorService;
import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.utils.ScannerUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.SimpleApplicationConsumer;
import org.jtheque.utils.ui.ImageUtils;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A files service implementation.
 *
 * @author Baptiste Wicht
 */
public final class FFMpegService implements IFFMpegService {
    private static final Pattern PATTERN = Pattern.compile(", ");
    private static final Random RANDOM = new Random();

    private static final int THUMBNAIL_WIDTH = 200;
    private static final int MAX_RANDOM_TIME = 50;
    private static final Pattern MILLISECONDS_PATTERN = Pattern.compile("\\.");

    @Resource
    private IMoviesModule moviesModule;

    @Resource
    private IErrorService errorService;

    @Override
    public Resolution getResolution(File f) {
        if (testFFMpegIsInstalled()) {
            String line = ScannerUtils.getLineStartingWith(getInformations(f), "Stream #0.0: Video:");

            if(StringUtils.isNotEmpty(line)){
                String resolution = PATTERN.split(line)[2].trim();

                if (resolution.contains(" ")) {
                    resolution = resolution.substring(0, resolution.indexOf(' '));
                }

                return new Resolution(resolution);
            }
        }

        return null;
    }

    @Override
    public PreciseDuration getDuration(File f) {
        if (testFFMpegIsInstalled()) {
            String line = ScannerUtils.getLineStartingWith(getInformations(f), "Duration:");

            if(StringUtils.isNotEmpty(line)){
                return new PreciseDuration(formatDuration(line));
            }
        }

        return null;
    }

    /**
     * Format the duration of the movie.
     *
     * @param line The line of the duration.
     *
     * @return The duration. 
     */
    private static CharSequence formatDuration(String line) {
        StringBuilder duration = new StringBuilder(line.substring(10, line.indexOf(',')));

        String milliSeconds = MILLISECONDS_PATTERN.split(duration)[1];

        while(milliSeconds.length() != 3){
            if(milliSeconds.length() > 3){
                duration.deleteCharAt(duration.length() - 1);
            }

            if(milliSeconds.length() < 3){
                duration.append(0);
            }

            milliSeconds = MILLISECONDS_PATTERN.split(duration)[1];
        }

        return duration;
    }

    @Override
    public BufferedImage generateRandomPreviewImage(File f) {
        return generatePreviewImage(f, getRandomTime());
    }

    @Override
    public BufferedImage generatePreviewImage(File f, String time) {
        if (testFFMpegIsInstalled()) {
            String fileName = moviesModule.getThumbnailFolderPath() + "temp.jpg";

            SimpleApplicationConsumer p = new SimpleApplicationConsumer(moviesModule.getConfig().getFFmpegLocation(),
                    "-i", f.getAbsolutePath(),
                    "-f", "mjpeg",
                    "-t", "0.001",
                    "-ss", time,
                    "-y", fileName);

            try {
                p.consume();
            } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }

            return ImageUtils.createThumbnail(openImage(new File(fileName)), THUMBNAIL_WIDTH);
        }

        return null;
    }

    @Override
    public BufferedImage generateImageFromUserInput(File file) {
        return ImageUtils.createThumbnail(openImage(file), THUMBNAIL_WIDTH);
    }

    /**
     * Open the image specified by a file.
     *
     * @param file The file of the image.
     *
     * @return The thumbnail of the image.
     */
    private static BufferedImage openImage(File file) {
        return ImageUtils.openCompatibleImage(file);
    }

    /**
     * Indicate if FFMpeg is installed or not. If it's not installed, this method will display an error message.
     *
     * @return true if it's installed else false.
     */
    private boolean testFFMpegIsInstalled() {
        boolean notInstalled = !ffmpegIsInstalled();

        if (notInstalled) {
            errorService.addInternationalizedError("movie.errors.ffmpeg");
        }

        return !notInstalled;
    }

    @Override
    public boolean ffmpegIsInstalled() {
        IMovieConfiguration config = moviesModule.getConfig();

        return StringUtils.isNotEmpty(config.getFFmpegLocation()) && new File(config.getFFmpegLocation()).exists();
    }

    /**
     * Return the FFmpeg informations of the specified files.
     *
     * @param f The file to the informations from.
     * @return A Scanner on the informations result.
     */
    private Scanner getInformations(File f) {
        IMovieConfiguration config = moviesModule.getConfig();

        SimpleApplicationConsumer p = new SimpleApplicationConsumer(config.getFFmpegLocation(), "-i", f.getAbsolutePath());

        try {
            p.consume();

            return new Scanner(p.getResult());
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Return a random time to generate random preview image.
     *
     * @return A random time to generate with ffmpeg.
     */
    private static String getRandomTime() {
        return String.valueOf(RANDOM.nextInt(MAX_RANDOM_TIME) + 1);
	}
}