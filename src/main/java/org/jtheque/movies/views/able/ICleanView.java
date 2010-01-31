package org.jtheque.movies.views.able;

import org.jtheque.core.managers.view.able.IView;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.movies.views.able.models.ICleanModel;

import java.util.Collection;

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

/**
 * A view specification to clean the name of a movie.
 *
 * @author Baptiste Wicht
 */
public interface ICleanView extends IView {
    /**
     * Return all the selected cleaners.
     *
     * @return A Collection containing all the selected cleaners.
     */
    Collection<NameCleaner> getSelectedCleaners();

    /**
     * Indicate if the subcategories are included in the clean process.
     *
     * @return true if we must include the subcategories else false.
     */
    boolean areSubCategoriesIncluded();

    @Override
    ICleanModel getModel();
}