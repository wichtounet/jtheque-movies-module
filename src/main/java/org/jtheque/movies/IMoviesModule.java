package org.jtheque.movies;

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
 * A movies module specification.
 *
 * @author Baptiste Wicht
 */
public interface IMoviesModule {
    String IMAGES_BASE_NAME = "org/jtheque/movies/images";

    /**
     * Return the configuration of the module.
     *
     * @return The configuration of the module.
     */
    IMovieConfiguration getConfig();

    /**
     * Return the folder containing the thumbnails of the application.
     *
     * @return The path to the folder containing the thumbnails.
     */
    String getThumbnailFolderPath();
}