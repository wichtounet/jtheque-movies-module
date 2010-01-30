package org.jtheque.movies.controllers.impl;

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

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.movies.controllers.able.ICleanController;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.ICleanView;
import org.jtheque.movies.views.able.models.ICleanModel;

import javax.annotation.Resource;

/**
 * Controller for the clean view.
 *
 * @author Baptiste Wicht
 */
public final class CleanController extends AbstractController implements ICleanController {
    @Resource
    private ICleanView cleanView;

    @Resource
    private IMoviesService moviesService;

    @Resource
    private IMovieController movieController;

    @Override
    public ICleanView getView(){
        return cleanView;
    }

	@Override
	public void clean(Movie movie){
		getViewModel().setMovie(movie);
		cleanView.display();
	}

	@Override
	public void clean(Category category){
		getViewModel().setCategory(category);
		cleanView.display();
	}

	@Override
	public void clean(){
		if(getViewModel().isMovieMode()){
			moviesService.clean(getViewModel().getMovie(), cleanView.getSelectedCleaners());
			
        	movieController.getView().refreshData();
		} else {
			moviesService.clean(
					moviesService.getMovies(getViewModel().getCategory(), cleanView.areSubCategoriesIncluded()), 
					cleanView.getSelectedCleaners());
		}

        cleanView.closeDown();
	}

	private ICleanModel getViewModel(){
		return cleanView.getModel();
	}
}