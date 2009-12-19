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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;

/**
 * A file parser who extracts the category from the start of the file name to a specified character.
 *
 * @author Baptiste Wicht
 */
public final class ToCharCategoryParser implements FileParser {
    private Category category;
    private final String character;

    @Resource
    private ICategoriesService categoriesService;

    /**
     * Construct a new ToCharCategoryParser with the specified end character.
     *
     * @param character The end character.
     */
    public ToCharCategoryParser(String character){
        super();

        this.character = character;
    }

    @Override
    public String getTitle(){
        return Managers.getManager(ILanguageManager.class).getMessage("movie.auto.parser.to.char", "-");
    }

    @Override
    public void parseFilePath(File file){
        if (file.isFile()){
            String name = file.getName().substring(0, file.getName().indexOf(character)).trim();

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
        if (!fileName.contains(character)){
            return fileName;
        }

        return fileName.substring(fileName.indexOf(character) + 1);
    }

    @Override
    public Collection<Category> getExtractedCategories(){
        if (category == null){
            return CollectionUtils.emptyList();
        }

        return Arrays.asList(category);
    }
}