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
import org.jtheque.movies.services.able.ICategoriesService;

import javax.annotation.Resource;
import java.io.File;

/**
 * A file parser who extracts the category from the start of the file name to a specified character.
 *
 * @author Baptiste Wicht
 */
public final class ToCharCategoryParser extends AbstractSimpleCategoryParser {
    private final String character;

    /**
     * Construct a new ToCharCategoryParser with the specified end character.
     *
     * @param character The end character.
     */
    public ToCharCategoryParser(String character) {
        super();

        this.character = character;
    }

    @Override
    public String getTitle() {
        return CoreUtils.getMessage("movie.auto.parser.to.char", "-");
    }

    @Override
    public void parseFilePath(File file) {
        if (file.isFile()) {
            String name = file.getName().substring(0, file.getName().indexOf(character)).trim();

            addCategory(name);
        }
    }

    @Override
    public String clearFileName(String fileName) {
        if (!fileName.contains(character)) {
            return fileName;
        }

        return fileName.substring(fileName.indexOf(character) + 1);
    }
}