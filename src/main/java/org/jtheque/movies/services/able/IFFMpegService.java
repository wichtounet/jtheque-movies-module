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
    Resolution getResolution(File f);

    PreciseDuration getDuration(File f);

	BufferedImage generateRandomPreviewImage(File file);

	BufferedImage generatePreviewImage(File file, String time);

	BufferedImage generateImageFromUserInput(File file);
}