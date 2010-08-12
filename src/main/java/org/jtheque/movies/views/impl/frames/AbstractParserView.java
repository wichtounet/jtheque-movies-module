package org.jtheque.movies.views.impl.frames;

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
import org.jtheque.movies.views.impl.panel.containers.CustomParserContainer;
import org.jtheque.movies.views.impl.panel.containers.ParserContainer;
import org.jtheque.movies.views.impl.panel.containers.SimpleParserContainer;
import org.jtheque.ui.Model;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User interface to add a movie from a file.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractParserView extends SwingFilthyBuildedDialogView<Model> {
    private final Collection<ParserContainer> parserContainers;

    /**
     * Construct a new Category View.
     *
     * @param parsers The category parsers.
     */
    AbstractParserView(Collection<FileParser> parsers) {
        super();

        parserContainers = new ArrayList<ParserContainer>(parsers.size());

        for (FileParser p : parsers) {
            if (p.hasCustomView()) {
                parserContainers.add(new CustomParserContainer(p));
            } else {
                parserContainers.add(new SimpleParserContainer(p));
            }
        }
    }

    /**
     * Return all the parser containers of the view.
     *
     * @return An Iterable for all the parsers containers.
     */
    final Iterable<ParserContainer> getContainers() {
        return parserContainers;
    }

    /**
     * Return all the selected parsers.
     *
     * @return A Collection containing all the selected parsers.
     */
    public final Collection<FileParser> getSelectedParsers() {
        Collection<FileParser> parsers = new ArrayList<FileParser>(5);

        for (ParserContainer container : parserContainers) {
            if (container.isSelected()) {
                parsers.add(container.getParser());
            }
        }

        return parsers;
    }
}