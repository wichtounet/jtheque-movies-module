package org.jtheque.movies.controllers.able;

import org.jtheque.movies.views.able.IImageView;
import org.jtheque.views.able.Controller;

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
 * An image controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IImageController extends Controller {
    /**
     * Edit the image of the current movie.
     */
    void editImage();

    @Override
    IImageView getView();

    /**
     * Generate a preview image from an image give by the user.
     *
     * @param imagePath The path to the user image.
     */
    void generateFileImage(String imagePath);

    /**
     * Generate a random image from the file of the movie.
     */
    void generateRandomImage();

    /**
     * Generate an image at the specific time of the movie.
     *
     * @param time The time (in seconds) at when we must generate the image.
     */
    void generateTimeImage(String time);

    /**
     * Save the current image to the current movie.
     */
    void save();
}
