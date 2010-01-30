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

	Set<Movie> getMovies(Category category, boolean includeSubCategory);

	void clean(Movie movie, Collection<NameCleaner> selectedCleaners);

    /**
     * Clean all the movies using the specified cleaners.
     *
     * @param movies   The movies to clean.
     * @param cleaners The cleaners to use to clean the movies.
     */
    void clean(Collection<Movie> movies, Collection<NameCleaner> cleaners);

	boolean fileExists(String file);

    boolean fileExistsInOtherMovie(Movie movie, String file);

	void saveImage(Movie movie, BufferedImage image);

    void fillInformations(Set<Movie> movies, boolean duration, boolean resolution, boolean image);

	Movie getMovie(String title);
}
