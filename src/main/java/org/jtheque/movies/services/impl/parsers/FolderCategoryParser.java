package org.jtheque.movies.services.impl.parsers;

import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.Resource;
import javax.swing.JComponent;
import java.io.File;
import java.util.Arrays;
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
 * A file parser to extract the category from the parent folder.
 *
 * @author Baptiste Wicht
 */
public final class FolderCategoryParser implements FileParser {
    private Category category;

    @Resource
    private ICategoriesService categoriesService;

    @Override
    public String getTitle(){
        return CoreUtils.getMessage("movie.auto.parser.folder");
    }

    @Override
    public void parseFilePath(File file){
        if (file.isFile()){
            String name = file.getParentFile().getName();

            if (categoriesService.exists(name)){
                category = categoriesService.getCategory(name);
            } else {
                category = categoriesService.getEmptyCategory();
                category.setTitle(name);
            }
        }
    }

    @Override
    public String clearFileName(String fileName){
        return fileName;
    }

    @Override
    public Collection<Category> getExtractedCategories(){
        if (category == null){
            return CollectionUtils.emptyList();
        }

        return Arrays.asList(category);
    }

	@Override
	public boolean hasCustomView(){
		return false;
	}

	@Override
	public JComponent getCustomView(){
		return null;
	}
}