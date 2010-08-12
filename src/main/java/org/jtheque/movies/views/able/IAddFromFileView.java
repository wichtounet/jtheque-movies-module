package org.jtheque.movies.views.able;

import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.ui.View;

import java.util.Collection;

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

/**
 * A view to add a movie directly from a file.
 *
 * @author Baptiste Wicht
 */
public interface IAddFromFileView extends View {
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