package org.jtheque.movies.views.impl.models;

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

import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.models.IMoviesModel;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.PrincipalDataModel;

import java.util.Collection;

/**
 * A model for the movies view.
 *
 * @author Baptiste Wicht
 */
public final class MoviesModel extends PrincipalDataModel<Movie> implements IMoviesModel {
    private Movie currentMovie;

    /**
     * Construct a new MoviesModel.
     */
    public MoviesModel() {
        super();

        CoreUtils.<IMoviesService>getBean("moviesService").addDataListener(this);
    }

    @Override
    public Collection<Movie> getDatas() {
        return CoreUtils.<IMoviesService>getBean("moviesService").getDatas();
    }

    @Override
    public Movie getCurrentMovie() {
        return currentMovie;
    }

    @Override
    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;

        fireCurrentObjectChanged(new ObjectChangedEvent(this, currentMovie));
    }
}