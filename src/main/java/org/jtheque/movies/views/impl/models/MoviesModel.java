package org.jtheque.movies.views.impl.models;

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

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.models.IMoviesModel;
import org.jtheque.primary.utils.views.listeners.ObjectChangedEvent;
import org.jtheque.primary.utils.views.PrincipalDataModel;

import java.util.Collection;

/**
 * A model for the movies view.
 *
 * @author Baptiste Wicht
 */
public final class MoviesModel extends PrincipalDataModel<Movie> implements IMoviesModel {
    private Movie currentMovie;

	private final IMoviesService moviesService;

	/**
     * Construct a new MoviesModel.
     */
    public MoviesModel(IMoviesService moviesService) {
        super();
		
		this.moviesService = moviesService;

		moviesService.addDataListener(this);
    }

    @Override
    public Collection<Movie> getDatas() {
        return moviesService.getDatas();
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