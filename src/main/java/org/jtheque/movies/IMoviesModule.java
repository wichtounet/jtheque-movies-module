package org.jtheque.movies;

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
}