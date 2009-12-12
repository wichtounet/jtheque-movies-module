package org.jtheque.movies.views.able;

import org.jtheque.core.managers.view.able.IView;
import org.jtheque.movies.services.impl.parsers.FileParser;

import java.io.File;
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
     * 
     */
    void removeSelectedFile();

    /**
     * Validate the content of the view at the specified phase. 
     * 
     * @param phase The phase to validate. 
     * 
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
     * Clear the files list. 
     */
    void clearFiles();

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
