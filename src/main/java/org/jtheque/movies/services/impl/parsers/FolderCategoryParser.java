package org.jtheque.movies.services.impl.parsers;

import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;

import javax.annotation.Resource;
import java.io.File;

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
 * A file parser to extract the category from the parent folder.
 *
 * @author Baptiste Wicht
 */
public final class FolderCategoryParser extends AbstractSimpleCategoryParser {
    @Override
    public String getTitle() {
        return CoreUtils.getMessage("movie.auto.parser.folder");
    }

    @Override
    public void parseFilePath(File file) {
        if (file.isFile()) {
            String name = file.getParentFile().getName();

            addCategory(name);
        }
    }

    @Override
    public String clearFileName(String fileName) {
        return fileName;
    }
}