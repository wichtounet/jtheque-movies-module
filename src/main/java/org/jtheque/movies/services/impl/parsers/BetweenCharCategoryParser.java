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

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A category parser who extract the category between two chars.
 *
 * @author Baptiste Wicht
 */
public final class BetweenCharCategoryParser implements FileParser {
    private Collection<Category> categories;

    private final String characterStart;
    private final String characterEnd;

    @Resource
    private ICategoriesService categoriesService;

    /**
     * Construct a new BetweenCharCategoryParser.
     *
     * @param characterStart The start character.
     * @param characterEnd   The end character.
     */
    public BetweenCharCategoryParser(String characterStart, String characterEnd){
        super();

        this.characterStart = characterStart;
        this.characterEnd = characterEnd;
    }

    @Override
    public String getTitle(){
        return Managers.getManager(ILanguageManager.class).getMessage("movie.auto.parser.between.char", characterStart, characterEnd);
    }

    @Override
    public void parseFilePath(File file){
        if (file.isFile()){
            String fileName = file.getName();

            categories = new ArrayList<Category>(5);

            while (fileName.contains(characterStart) && fileName.contains(characterEnd)){
                String name = fileName.substring(fileName.indexOf(characterStart) + 1, fileName.indexOf(characterEnd));

                if (categoriesService.exists(name)){
                    categories.add(categoriesService.getCategory(name));
                } else {
                    Category category = categoriesService.getEmptyCategory();
                    category.setTitle(name);

                    categories.add(category);
                }

                fileName = fileName.substring(fileName.indexOf(characterEnd) + 1);
            }
        }
    }

    @Override
    public String clearFileName(String fileName){
        String name = fileName;

        for (Category cat : categories){
            name = name.replace(characterStart + cat.getTitle() + characterEnd, "");
        }

        return name;
    }

    @Override
    public Collection<Category> getExtractedCategories(){
        return categories;
    }
}