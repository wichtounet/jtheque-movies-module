package org.jtheque.movies.controllers.able;

import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.views.able.IAddFromFileView;

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
 * An add from file controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IAddFromFileController extends Controller {
    @Override
    IAddFromFileView getView();

    /**
     * Add a movie from the specified file path and parsers.
     *
     * @param filePath The path to the file of the movie.
     * @param parsers  The parsers to use to generate the categories of the movie.
     */
    void add(String filePath, Collection<FileParser> parsers);
}