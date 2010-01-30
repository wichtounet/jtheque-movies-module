package org.jtheque.movies.views.able.models;

import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;

public interface ICleanModel extends IModel {
    void setCategory(Category category);

    void setMovie(Movie movie);

    Category getCategory();

    Movie getMovie();

    boolean isMovieMode();
}
