package org.jtheque.movies.views.able;

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

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.ui.able.WindowView;

import java.awt.image.BufferedImage;

/**
 * An image view specification. This view enable the user to generate and configure a preview image for a movie.
 *
 * @author Baptiste Wicht
 */
public interface IImageView extends WindowView {
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
