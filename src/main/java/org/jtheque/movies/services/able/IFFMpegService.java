package org.jtheque.movies.services.able;

import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;

import java.awt.image.BufferedImage;
import java.io.File;

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

/**
 * A files service specification.
 *
 * @author Baptiste Wicht
 */
public interface IFFMpegService {
    /**
     * Return the resolution of the movie specified by the file.
     *
     * @param f The file of the movie.
     * @return The resolution of the file else null if this not possible to get that information.
     */
    Resolution getResolution(File f);

    /**
     * Return the duration of the movie specified by the file.
     *
     * @param f The file of the movie.
     * @return The duration of the file else null if this not possible to get that information.
     */
    PreciseDuration getDuration(File f);

    /**
     * Generate a random preview image from the movie file.
     *
     * @param file The file of the movie.
     * @return A random preview image else null if this not possible to get that information.
     */
    BufferedImage generateRandomPreviewImage(File file);

    /**
     * Generate a preview image at the specified time.
     *
     * @param file The file of the movie.
     * @param time The time at which we must get the image.
     * @return The preview image at the specified time else null if this not possible to get that information.
     */
    BufferedImage generatePreviewImage(File file, String time);

    /**
     * Generate a preview image from a file specified by the user.
     *
     * @param file The file specified by the user.
     * @return The image specified by the user scaled and stored to be a preview image else
     *         null if this not possible to get that information.
     */
    BufferedImage generateImageFromUserInput(File file);
}