package org.jtheque.movies.services.able;

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

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.persistence.able.DataContainer;
import org.jtheque.primary.able.services.DataService;

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
     *
     * @return A Set containing all the movies of the specified category and eventually the subcategories if specified.
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
     *
     * @return true if the file exists else false.
     */
    boolean fileExists(String file);

    /**
     * Indicate if the specified file exists in the application in an other movie than the specified one.
     *
     * @param movie The movie to exclude from the search.
     * @param file  The file to search for.
     *
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
     *
     * @return The Movie with the specified title if there is one else null.
     */
    Movie getMovie(String title);

    /**
     * Indicate if a thumbnail is not used in any movies.
     *
     * @param name The name of the thumbnail.
     *
     * @return true if the thumbnail is not used else false.
     */
    boolean thumbnailIsNotUsed(String name);

    /**
     * Return all the movies with invalid files.
     *
     * @return A Collection containing all the movies with invalid files.
     */
    Collection<? extends Movie> getMoviesWithInvalidFiles();
}
