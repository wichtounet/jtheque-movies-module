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
 * @author Baptiste Wicht
 */
public interface IMovieConfiguration {
    /**
     * Return the system to open the movie.
     *
     * @return The opening system for movie.
     */
    Opening getOpeningSystem();

    /**
     * Set the opening system for movies.
     *
     * @param opening The opening system.
     */
    void setOpeningSystem(Opening opening);

    /**
     * Return the location of the FFMpeg.exe file.
     *
     * @return The location of FFMpeg file.
     */
    String getFFmpegLocation();

    /**
     * Set the location of the FFMpeg.exe file.
     *
     * @param location The location of FFmpeg file.
     */
    void setFFmpegLocation(String location);

    /**
     * An enum for different opening system.
     *
     * @author Baptiste Wicht
     */
    enum Opening {
        SYSTEM("movie.config.opening.system"),
        VLC("movie.config.opening.vlc"),
        WMP("movie.config.opening.wmp");

        private final String value;

        /**
         * Construct a new Opening.
         *
         * @param value The string internationalization key.
         */
        Opening(String value) {
            this.value = value;
        }

        /**
         * Return the internationalization key of the opening system.
         *
         * @return The i18n key.
         */
        String getValue() {
            return value;
        }
    }
}
