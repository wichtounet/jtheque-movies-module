package org.jtheque.movies.controllers.able;

import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.movies.views.able.IGenerateInfosView;

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
 * A controller to generate informations for all the movies of a category specification.
 *
 * @author Baptiste Wicht
 */
public interface IGenerateInfosController extends Controller {
    @Override
    IGenerateInfosView getView();

    /**
     * Generate the informations using the informations of the view.
     */
    void generate();
}