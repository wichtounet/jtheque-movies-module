package org.jtheque.movies.services.able;

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

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.primary.services.able.DataService;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Set;

/**
 * @author Baptiste Wicht
 */
public interface IMoviesService extends DataContainer<Movie>, DataService<Movie> {
    String DATA_TYPE = "Movies";

    /**
     * Create an empty film.
     *
     * @return An empty film.
     */
    Movie getEmptyMovie();

    /**
     * Return the movies of the current collection.
     *
     * @return A List containing all the movies of the current collection.
     */
    Collection<Movie> getMovies();

    /**
     * Return all the movies of the specified category, including or not the movies of the subcategories.
     *
     * @param category           The category to get the movies from.
     * @param includeSubCategory boolean tag indicating if we must include (true) the subcategories or not (false).
     * @return A Set containing all the movies of the specified category and eventually the subcategories if
     *         specified.
     */
    Set<Movie> getMovies(Category category, boolean includeSubCategory);

    /**
     * Clean the specified movie title with the specified cleaners.
     *
     * @param movie            The movie to clean.
     * @param selectedCleaners The cleaners to use to clean the movie's title.
     */
    void clean(Movie movie, Collection<NameCleaner> selectedCleaners);

    /**
     * Clean all the movies using the specified cleaners.
     *
     * @param movies   The movies to clean.
     * @param cleaners The cleaners to use to clean the movies.
     */
    void clean(Collection<Movie> movies, Collection<NameCleaner> cleaners);

    /**
     * Indicate if the specified file still exists in the application.
     *
     * @param file The file to test.
     * @return true if the file exists else false.
     */
    boolean fileExists(String file);

    /**
     * Indicate if the specified file exists in the application in an other movie than the specified one.
     *
     * @param movie The movie to exclude from the search.
     * @param file  The file to search for.
     * @return true if the file in an other movie than the specified one else false.
     */
    boolean fileExistsInOtherMovie(Movie movie, String file);

    /**
     * Save the specified image and set it as the preview image of the movie.
     *
     * @param movie The movie to set the image to. .
     * @param image The image to save.
     */
    void saveImage(Movie movie, BufferedImage image);

    /**
     * Fill the informations of the specified movies.
     *
     * @param movies     The movies to fill the informations for.
     * @param duration   A boolean tag indicating if we must fill the duration (true) or not (false).
     * @param resolution A boolean tag indicating if we must fill the resolution (true) or not (false).
     * @param image      A boolean tag indicating if we must fill the image (true) or not (false).
     */
    void fillInformations(Set<Movie> movies, boolean duration, boolean resolution, boolean image);

    /**
     * Return the movie of the specified title.
     *
     * @param title The title to search for.
     * @return The Movie with the specified title if there is one else null.
     */
	Movie getMovie(String title);
}
