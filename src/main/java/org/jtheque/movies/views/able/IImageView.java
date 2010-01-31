package org.jtheque.movies.views.able;

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

import org.jtheque.core.managers.view.able.IWindowView;
import org.jtheque.movies.persistence.od.able.Movie;

import java.awt.image.BufferedImage;

/**
 * An image view specification. This view enable the user to generate and configure a
 * preview image for a movie.
 *
 * @author Baptiste Wicht
 */
public interface IImageView extends IWindowView {
    /**
     * Display the specified movie in the view.
     *
     * @param movie The movie to display.
     */
    void displayMovie(Movie movie);

    /**
     * Return the time entered by the user.
     *
     * @return The time entered by the user.
     */
    String getTime();

    /**
     * Return the path to the image entered by the user.
     *
     * @return The path to the image.
     */
    String getImagePath();

    /**
     * Set the preview image.
     *
     * @param image The preview image.
     */
    void setImage(BufferedImage image);

    /**
     * Return the current preview image.
     *
     * @return The current preview image.
     */
    BufferedImage getImage();
}
