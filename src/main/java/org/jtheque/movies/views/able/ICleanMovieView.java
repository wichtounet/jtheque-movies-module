package org.jtheque.movies.views.able;

import org.jtheque.core.managers.view.able.IView;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;

import java.util.Collection;

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
 * A view specification to clean the name of a movie. 
 * 
 * @author Baptiste Wicht
 */
public interface ICleanMovieView extends IView {
    /**
     * Open the view to select the options to clean the name of the movie. 
     * 
     * @param movie The movie to clean the name for. 
     */
    void clean(Movie movie);

    /**
     * Return all the selected cleaners. 
     * 
     * @return A Collection containing all the selected cleaners. 
     */
    Collection<NameCleaner> getSelectedCleaners();

    /**
     * Return all the movies to be cleaned. 
     * 
     * @return A Collection containing all the movies to be cleaned. 
     */
    Collection<Movie> getMovies();

    /**
     * Open the view to clean the specified movies. 
     * 
     * @param movies The movies to clean. 
     */
    void clean(Collection<Movie> movies);
}