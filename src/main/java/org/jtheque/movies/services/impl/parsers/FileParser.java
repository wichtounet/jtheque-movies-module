package org.jtheque.movies.services.impl.parsers;

import org.jtheque.movies.persistence.od.able.Category;

import javax.swing.JComponent;

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
    String getTitleKey();

    /**
     * Return all the replaces for the title.
     *
     * @return The replaces for the title.
     */
    Object[] getTitleReplaces();

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

    /**
     * Indicate if the parser has a custom view or if we just need to use a simple check box with the
     * title of the parser.
     *
     * @return true if the parser has custom view else false.
     */
    boolean hasCustomView();

    /**
     * Return the custom view of the parser.
     *
     * @return The custom view of the parser or null if the parser has no custom view.
     * @see #hasCustomView()
     */
    JComponent getCustomView();
}