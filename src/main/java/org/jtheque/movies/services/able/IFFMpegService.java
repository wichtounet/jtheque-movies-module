package org.jtheque.movies.services.able;

import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;

import java.awt.image.BufferedImage;
import java.io.File;

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
    
    /**
     * Indicate if FFMpeg is installed or not.
     *
     * @return true if it's installed else false.
     */
    boolean ffmpegIsInstalled();
}