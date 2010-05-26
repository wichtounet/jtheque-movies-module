package org.jtheque.movies.views.able;

import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.ui.able.IView;

import java.io.File;
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
 * A view specification in which we can import movies from a folder.
 *
 * @author Baptiste Wicht
 */
public interface IImportFolderView extends IView {
    /**
     * Return the path to the specified folder.
     *
     * @return The path to the specified folder.
     */
    String getFolderPath();

    /**
     * Remove the selected file from the list.
     */
    void removeSelectedFile();

    /**
     * Validate the content of the view at the specified phase.
     *
     * @param phase The phase to validate.
     * @return true if the view is valid else false.
     */
    boolean validateContent(Phase phase);

    /**
     * Stop the wait animation.
     */
    void stopWait();

    /**
     * Start the wait animation.
     */
    void startWait();

    /**
     * Set the files of the view.
     *
     * @param files The files to display in the view.
     */
    void setFiles(Collection<File> files);

    /**
     * Return the files of the view.
     *
     * @return The files in the view.
     */
    Collection<File> getFiles();

    /**
     * Return all the selected file parser.
     *
     * @return A Collection containing all the file parser.
     */
    Collection<FileParser> getSelectedParsers();

    /**
     * The Phase.
     */
    enum Phase {
        CHOOSE_FOLDER,
        CHOOSE_FILES
    }
}
