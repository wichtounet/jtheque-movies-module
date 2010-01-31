package org.jtheque.movies;

import org.jtheque.core.utils.CoreUtils;

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

        @Override
        public String toString() {
            return CoreUtils.getMessage(value);
        }
    }
}
