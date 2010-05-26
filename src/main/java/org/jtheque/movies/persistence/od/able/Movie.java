package org.jtheque.movies.persistence.od.able;

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

import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.persistence.able.Note;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * A Movie Specification.
 *
 * @author Baptiste Wicht
 */
public interface Movie extends CollectionData {
    String TITLE = "movie.title";
    String FILE = "movie.file";
    String NOTE = "movie.note";
    String DURATION = "movie.duration";
    String RESOLUTION = "movie.resolution";

    int TITLE_LENGTH = 100;
    int FILE_LENGTH = 200;

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
    void addCategories(Collection<Category> categories);

    /**
     * Add a category to the movie.
     *
     * @param category The category to add.
     */
    void addCategory(Category category);

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

    /**
     * Return the duration of the movie.
     *
     * @return The duration of the movie.
     */
    PreciseDuration getDuration();

    /**
     * Set the duration of the movie.
     *
     * @param duration The duration of the movie.
     */
    void setDuration(PreciseDuration duration);

    /**
     * Return the resolution of the movie.
     *
     * @return The resolution of the movie.
     */
    Resolution getResolution();

    /**
     * Set the resolution of the movie.
     *
     * @param resolution The resolution of the movie.
     */
    void setResolution(Resolution resolution);

    /**
     * Return the image of the movie.
     *
     * @return The image of the movie.
     */
    String getImage();

    /**
     * Set the image of the movie.
     *
     * @param image The image of the movie.
     */
	void setImage(String image);

    /**
     * Set the categories of the movie.
     *
     * @param categories The categories of the movie. 
     */
    void setCategories(Collection<Category> categories);
}