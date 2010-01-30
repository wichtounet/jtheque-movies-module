package org.jtheque.movies.views.impl.choice;

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
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.primary.controller.impl.undo.GenericDataDeletedEdit;
import org.jtheque.primary.view.impl.choice.AbstractPrimaryDeleteChoiceAction;
import org.jtheque.primary.view.impl.choice.Deleter;

/**
 * An action to delete the selected item.
 *
 * @author Baptiste Wicht
 */
public final class DeleteChoiceAction extends AbstractPrimaryDeleteChoiceAction {
    /**
     * Construct a new DeleteChoiceAction.
     */
    public DeleteChoiceAction(){
        super();

        addDeleters(new MovieDeleter(), new CategoryDeleter());
		addPrimaryDeleters();
    }

    /**
     * A movie deleter.
     *
     * @author Baptiste Wicht.
     */
    private static final class MovieDeleter extends Deleter<Movie> {
        /**
         * Construct a new MovieDeleter.
         */
        private MovieDeleter(){
            super(IMoviesService.DATA_TYPE);
        }

        @Override
        public void delete(Movie o){
            addEditIfDeleted(
					CoreUtils.<IMoviesService>getBean("moviesService").delete(o),
					new GenericDataDeletedEdit<Movie>("moviesService", o));
        }
    }

    /**
     * A category deleter.
     *
     * @author Baptiste Wicht
     */
    private static final class CategoryDeleter extends Deleter<Category> {
        /**
         * Construct a new CategoryDeleter.
         */
        private CategoryDeleter(){
            super(ICategoriesService.DATA_TYPE);
        }

        @Override
        public void delete(Category o){
            addEditIfDeleted(
					CoreUtils.<ICategoriesService>getBean("categoriesService").delete(o),
					new GenericDataDeletedEdit<Category>("categoriesService", o));
        }
    }
}