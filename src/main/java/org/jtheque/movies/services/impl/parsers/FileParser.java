package org.jtheque.movies.services.impl.parsers;

import org.jtheque.movies.persistence.od.able.Category;

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
 * A parser to extract the categories from the path of a file.
 *
 * @author Baptiste Wicht
 */
public interface FileParser {
    /**
     * Return the internationalized title of the parser.
     *
     * @return The internationalized title of the parser.
     */
    String getTitle();

    /**
     * Parse the file path of the file to extract the categories.
     *
     * @param file The file to parse.
     */
    void parseFilePath(File file);

    /**
     * Clear the filename. It seems delete the parts referring to categories.
     *
     * @param fileName The file name to clear.
     * @return the cleared filename.
     */
    String clearFileName(String fileName);

    /**
     * Return all the extracted categories.
     *
     * @return A collection containing all the extracted categories.
     */
    Collection<Category> getExtractedCategories();
}
