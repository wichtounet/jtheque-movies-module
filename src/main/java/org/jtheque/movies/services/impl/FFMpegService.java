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
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.ImageUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
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
            Scanner scanner = getInformations(f);

            if (scanner != null) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();

                    if (line.startsWith("Stream #0.0: Video:")) {
                        String resolution = PATTERN.split(line)[2].trim();

                        if (resolution.contains(" ")) {
                            resolution = resolution.substring(0, resolution.indexOf(' '));
                        }

                        return new Resolution(resolution);
                    }
                }
            }
        }

        return null;
    }

    @Override
    public PreciseDuration getDuration(File f) {
        if (ffmpegIsInstalled()) {
            Scanner scanner = getInformations(f);

            if (scanner != null) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();

                    if (line.startsWith("Duration:")) {
                        return new PreciseDuration(formatDuration(line));
                    }
                }
            }
        }

        return null;
    }

    /**
     * Format the durationof the movie.
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

            return createThumbnail(openImage(new File(fileName)));
        }

        return null;
    }

    @Override
    public BufferedImage generateImageFromUserInput(File file) {
        return createThumbnail(openImage(file));
    }

    /**
     * Open the image specified by a file.
     *
     * @param file The file of the image.
     *
     * @return The thumbnail of the image.
     */
    private static BufferedImage openImage(File file) {
        InputStream stream = Managers.getManager(IResourceManager.class).getResourceAsStream("file:" + file.getAbsolutePath());

        BufferedImage image = null;
        try {
            image = ImageUtils.openCompatibleImage(stream);
        } catch (HeadlessException e){
            try {
                ImageIO.setUseCache(false);
                image = ImageIO.read(file);
            } catch (IOException e1) {
                Managers.getManager(ILoggingManager.class).getLogger(FFMpegService.class).error(e);
            }
        }
        return image;
    }

    /**
     * Create a thumbnail for the specified image. This method works in normal and in headless mode.
     *
     * @param image The image to create thumbnail from.
     *
     * @return A thumbnail.
     */
    private static BufferedImage createThumbnail(BufferedImage image) {
        try {
            return ImageUtils.createThumbnail(image, THUMBNAIL_WIDTH);
        } catch (HeadlessException e){
            return createNotCompatibleThumbnail(image, THUMBNAIL_WIDTH);
        }
    }

    /**
     * Create a thumbnail of an image.
     *
     * @param image              The source image.
     * @param requestedThumbSize The requested size.
     * @return The thumbnail
     */
    public static BufferedImage createNotCompatibleThumbnail(BufferedImage image, int requestedThumbSize) {
        float ratio = (float) image.getWidth() / (float) image.getHeight();
        int width = image.getWidth();
        boolean divide = requestedThumbSize < width;

        BufferedImage thumb = image;

        do {
            if (divide) {
                width /= 2;

                if (width < requestedThumbSize) {
                    width = requestedThumbSize;
                }
            } else {
                width *= 2;

                if (width > requestedThumbSize) {
                    width = requestedThumbSize;
                }
            }

            BufferedImage temp = new BufferedImage(width, (int) (width / ratio), image.getTransparency());

            Graphics2D g2 = temp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
            g2.dispose();

            thumb = temp;
        } while (width != requestedThumbSize);

        return thumb;
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