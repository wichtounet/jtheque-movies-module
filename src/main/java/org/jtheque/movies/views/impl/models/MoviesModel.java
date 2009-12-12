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

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.impl.models.able.IMoviesModel;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.PrincipalDataModel;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * A model for the movies view.
 *
 * @author Baptiste Wicht
 */
public final class MoviesModel extends PrincipalDataModel<Movie> implements IMoviesModel {
    private Movie currentMovie;

    private List<Movie> displayList;

    @Resource
    private IMoviesService moviesService;

    /**
     * Init the model.
     */
    @PostConstruct
    private void init() {
        moviesService.addDataListener(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateDisplayList(Collection<Movie> movies) {
        getDisplayList().clear();
        
        if (movies == null) {
            getDisplayList().addAll(moviesService.getMovies());
        } else {
            getDisplayList().addAll(movies);
        }

        fireDisplayListChanged();
    }

    @Override
    public void updateDisplayList() {
        updateDisplayList(null);
    }

    @Override
    public Collection<Movie> getDisplayList() {
        if (displayList == null) {
            displayList = CollectionUtils.copyOf(moviesService.getMovies());
        }

        return displayList;
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