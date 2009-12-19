package org.jtheque.movies.views.impl.choiceActions;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.impl.edits.delete.DeletedCategoryEdit;
import org.jtheque.movies.views.impl.edits.delete.DeletedMovieEdit;
import org.jtheque.primary.services.able.ITypesService;
import org.jtheque.primary.view.impl.choice.AbstractDeleteChoiceAction;
import org.jtheque.primary.view.impl.choice.Deleter;

import javax.annotation.Resource;

/**
 * An action to delete the selected item.
 *
 * @author Baptiste Wicht
 */
public final class DeleteChoiceAction extends AbstractDeleteChoiceAction {
    @Resource
    private ICategoriesService categoriesService;
    @Resource
    private IMoviesService moviesService;

    /**
     * Construct a new DeleteChoiceAction.
     */
    public DeleteChoiceAction(){
        super();

        setDeleters(new MovieDeleter(), new CategoryDeleter());
    }

    @Override
    public boolean canDoAction(String action){
        return "delete".equals(action);
    }

    @Override
    public void execute(){
        final boolean yes = Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("choice.dialogs.delete") + ' ' + getSelectedItem().toString(),
                Managers.getManager(ILanguageManager.class).getMessage("choice.dialogs.delete.title"));

        if (yes){
            delete();
        }
    }

    /**
     * A movie deleter.
     *
     * @author Baptiste Wicht.
     */
    private final class MovieDeleter extends Deleter {
        /**
         * Construct a new MovieDeleter.
         */
        private MovieDeleter(){
            super(ITypesService.DATA_TYPE);
        }

        @Override
        public void delete(Object o){
            boolean deleted = moviesService.delete((Movie) o);

            addEditIfDeleted(deleted, new DeletedMovieEdit((Movie) o));
        }
    }

    /**
     * A category deleter.
     *
     * @author Baptiste Wicht
     */
    private final class CategoryDeleter extends Deleter {
        /**
         * Construct a new CategoryDeleter.
         */
        private CategoryDeleter(){
            super(ICategoriesService.DATA_TYPE);
        }

        @Override
        public void delete(Object o){
            boolean deleted = categoriesService.delete((Category) o);

            addEditIfDeleted(deleted, new DeletedCategoryEdit((Category) o));
        }
    }
}