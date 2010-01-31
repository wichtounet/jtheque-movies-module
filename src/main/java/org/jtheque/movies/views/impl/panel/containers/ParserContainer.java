package org.jtheque.movies.views.impl.panel.containers;

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

import org.jtheque.movies.services.impl.parsers.FileParser;

import javax.swing.*;

/**
 * A parser container. It's simply a swing container for file parser.
 *
 * @author Baptiste Wicht
 */
public interface ParserContainer {
    /**
     * Return the file parser for this checkbox.
     *
     * @return The file parser.
     */
    FileParser getParser();

    /**
     * Indicate if the parser is selected or not.
     *
     * @return true if the file parser is selected else false.
     */
    boolean isSelected();

    /**
     * Return the implementation of the file parser.
     *
     * @return the component implementation.
     */
    JComponent getImpl();
}
