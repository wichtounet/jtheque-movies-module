package org.jtheque.movies.views.able;

import org.jtheque.core.managers.view.able.IView;
import org.jtheque.movies.services.impl.parsers.FileParser;

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
 * A view to add a movie directly from a file. 
 * 
 * @author Baptiste Wicht
 */
public interface IAddFromFileView extends IView {
    /**
     * Return the file path. 
     * 
     * @return The path to the chosen file. 
     */
    String getFilePath();

    /**
     * Return the selected parsers. 
     * 
     * @return A Collection containing all the selected parsers. 
     */
    Collection<FileParser> getSelectedParsers();
}