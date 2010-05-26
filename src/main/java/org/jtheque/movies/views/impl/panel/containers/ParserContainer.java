package org.jtheque.movies.views.impl.panel.containers;

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

import org.jtheque.movies.services.impl.parsers.FileParser;

import javax.swing.JComponent;

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
