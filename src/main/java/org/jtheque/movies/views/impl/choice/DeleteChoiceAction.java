package org.jtheque.movies.views.impl.choice;

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

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.primary.utils.edits.GenericDataDeletedEdit;
import org.jtheque.primary.utils.choice.AbstractPrimaryDeleteChoiceAction;
import org.jtheque.primary.utils.choice.Deleter;

import javax.annotation.Resource;

/**
 * An action to delete the selected item.
 *
 * @author Baptiste Wicht
 */
public final class DeleteChoiceAction extends AbstractPrimaryDeleteChoiceAction {
	@Resource
	private IMoviesService moviesService;

	@Resource
	private ICategoriesService categoriesService;

    /**
     * Construct a new DeleteChoiceAction.
     */
    public DeleteChoiceAction() {
        super();

        addDeleters(new MovieDeleter(), new CategoryDeleter());
        addPrimaryDeleters();
    }

    /**
     * A movie deleter.
     *
     * @author Baptiste Wicht.
     */
    private final class MovieDeleter extends Deleter<Movie> {
        /**
         * Construct a new MovieDeleter.
         */
        private MovieDeleter() {
            super(IMoviesService.DATA_TYPE);
        }

        @Override
        public void delete(Movie o) {
            addEditIfDeleted(
                    moviesService.delete(o),
                    new GenericDataDeletedEdit<Movie>(moviesService, o));
        }
    }

    /**
     * A category deleter.
     *
     * @author Baptiste Wicht
     */
    private final class CategoryDeleter extends Deleter<Category> {
        /**
         * Construct a new CategoryDeleter.
         */
        private CategoryDeleter() {
            super(ICategoriesService.DATA_TYPE);
        }

        @Override
        public void delete(Category o) {
            addEditIfDeleted(
                    categoriesService.delete(o),
                    new GenericDataDeletedEdit<Category>(categoriesService, o));
        }
    }
}