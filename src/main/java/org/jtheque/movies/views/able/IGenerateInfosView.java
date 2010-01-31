package org.jtheque.movies.views.able;

import org.jtheque.core.managers.view.able.IView;
import org.jtheque.movies.persistence.od.able.Category;

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
public interface IGenerateInfosView extends IView {
    /**
     * Indicate if the user want to generate the duration.
     *
     * @return true if the user want to generate the duration else false.
     */
    boolean mustGenerateDuration();

    /**
     * Indicate if the user want to generate the resolution.
     *
     * @return true if the user want to generate the resolution else false.
     */
    boolean mustGenerateResolution();

    /**
     * Indicate if the user want to generate the image.
     *
     * @return true if the user want to generate the image else false.
     */
    boolean mustGenerateImage();

    /**
     * Indicate if the user want include the subcategories or not.
     *
     * @return true if the user want to generate the informations for the subcategories to else false.
     */
    boolean areSubCategoriesIncluded();

    /**
     * Return the selected category.
     *
     * @return The selected category.
     */
    Category getSelectedCategory();
}