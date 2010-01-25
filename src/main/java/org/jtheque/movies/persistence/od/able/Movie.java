package org.jtheque.movies.persistence.od.able;

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

import org.jtheque.core.utils.db.Note;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.primary.od.able.Collection;
import org.jtheque.primary.od.able.Data;

import java.util.Date;
import java.util.Set;

/**
 * A Movie Specification.
 *
 * @author Baptiste Wicht
 */
public interface Movie extends Data {
	String TITLE = "movie.title";
	String FILE = "movie.file";
	String NOTE = "movie.note";
	String DURATION = "movie.duration";
	String RESOLUTION = "movie.resolution";

	int TITLE_LENGTH = 100;
	int FILE_LENGTH = 200;

    /**
     * Return the collection.
     *
     * @return The collection.
     */
    Collection getTheCollection();

    /**
     * Set the collection of the movie.
     *
     * @param theCollection The collection of the movie.
     */
    void setTheCollection(Collection theCollection);

    /**
     * Return the title.
     *
     * @return The title.
     */
    String getTitle();

    /**
     * Set the title.
     *
     * @param title The title.
     */
    void setTitle(String title);

    /**
     * Return the categories of the movie.
     *
     * @return The categories of the movie.
     */
    Set<Category> getCategories();

    /**
     * Return the path to the file.
     *
     * @return The path to the file.
     */
    String getFile();

    /**
     * Set the path to the file.
     *
     * @param file The path to the file.
     */
    void setFile(String file);

    /**
     * Return the note of the movie.
     *
     * @return The note of the movie.
     */
    Note getNote();

    /**
     * Set the note of the movie.
     *
     * @param note The note of the movie.
     */
    void setNote(Note note);

    /**
     * Add categories to the movie.
     *
     * @param categories The categories to add.
     */
    void addCategories(java.util.Collection<Category> categories);

    /**
     * Add a category to the movie.
     *
     * @param category The category to add.
     */
    void addCategory(Category category);

    /**
     * Test if the movie is in the specified collection.
     *
     * @param collection The collection to test.
     * @return true if the movie is in the specified collection else false.
     */
    boolean isInCollection(Collection collection);

    /**
     * Test if the movie has categories else false.
     *
     * @return true if the movie has one or more categories else false.
     */
    boolean hasCategories();

    /**
     * Indicate if the movie if of the specified category.
     *
     * @param category The searched category.
     * @return true if the movie is of the specified category else false.
     */
    boolean isOfCategory(Category category);

    /**
     * Return the last modified date of the file.
     *
     * @return The last modified date of the file.
     */
    Date getFileLastModifiedDate();

    /**
     * Return the size of the file.
     *
     * @return The size of the file.
     */
    long getFileSize();

	PreciseDuration getDuration();

	void setDuration(PreciseDuration duration);

	Resolution getResolution();

	void setResolution(Resolution resolution);
}
