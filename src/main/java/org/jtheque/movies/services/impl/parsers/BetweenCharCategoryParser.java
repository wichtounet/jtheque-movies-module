package org.jtheque.movies.services.impl.parsers;

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

import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.persistence.od.able.Category;

import java.io.File;

/**
 * A category parser who extract the category between two chars.
 *
 * @author Baptiste Wicht
 */
public final class BetweenCharCategoryParser extends AbstractSimpleCategoryParser {
    private final String characterStart;
    private final String characterEnd;

    /**
     * Construct a new BetweenCharCategoryParser.
     *
     * @param characterStart The start character.
     * @param characterEnd   The end character.
     */
    public BetweenCharCategoryParser(String characterStart, String characterEnd) {
        super();

        this.characterStart = characterStart;
        this.characterEnd = characterEnd;
    }

    @Override
    public String getTitle() {
        return CoreUtils.getMessage("movie.auto.parser.between.char", characterStart, characterEnd);
    }

    @Override
    public void parseFilePath(File file) {
        if (file.isFile()) {
            String fileName = file.getName();

            while (fileName.contains(characterStart) && fileName.contains(characterEnd)) {
                String name = fileName.substring(fileName.indexOf(characterStart) + 1, fileName.indexOf(characterEnd));

                addCategory(name);

                fileName = fileName.substring(fileName.indexOf(characterEnd) + 1);
            }
        }
    }

    @Override
    public String clearFileName(String fileName) {
        String name = fileName;

        for (Category cat : getExtractedCategories()) {
            name = name.replace(characterStart + cat.getTitle() + characterEnd, "");
        }

        return name;
    }
}