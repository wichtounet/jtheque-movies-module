package org.jtheque.movies.views.impl.fb;

import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.core.utils.db.Note;

import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public interface IMovieFormBean extends FormBean {
    /**
     * Set the title.
     *
     * @param title The title.
     */
    void setTitle(String title);

    /**
     * Set the categories of the movie.
     *
     * @param categories The categories of the movie.
     */
    void setCategories(Collection<Category> categories);

    /**
     * Set the path to the file.
     *
     * @param file The path to the file.
     */
    void setFile(String file);

    /**
     * Set the note of the movie.
     *
     * @param note The note of the movie.
     */
    void setNote(Note note);

    /**
     * Fill the movie with the infos of the form bean. 
     * 
     * @param movie The movie to fill. 
     */
    void fillMovie(Movie movie);
}
