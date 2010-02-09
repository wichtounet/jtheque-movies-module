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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.utils.ScannerUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.SimpleApplicationConsumer;
import org.jtheque.utils.ui.ImageUtils;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    @Override
    public Resolution getResolution(File f) {
        if (ffmpegIsInstalled()) {
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
        if (ffmpegIsInstalled()) {
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
        if (ffmpegIsInstalled()) {
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
                CoreUtils.getLogger(getClass()).error(e);
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
        if(ImageUtils.isHeadless()){
            return ImageUtils.read(file);
        }

        InputStream stream = Managers.getManager(IResourceManager.class).getResourceAsStream("file:" + file.getAbsolutePath());

        return ImageUtils.openCompatibleImage(stream);
    }

    /**
     * Indicate if FFMpeg is installed or not.
     *
     * @return true if it's installed else false.
     */
    private boolean ffmpegIsInstalled() {
        IMovieConfiguration config = moviesModule.getConfig();

        boolean notInstalled = StringUtils.isEmpty(config.getFFmpegLocation()) || !new File(config.getFFmpegLocation()).exists();

        if (notInstalled) {
            Managers.getManager(IViewManager.class).displayError(new InternationalizedError("movie.errors.ffmpeg"));
        }

        return !notInstalled;
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
            CoreUtils.getLogger(getClass()).error(e);
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