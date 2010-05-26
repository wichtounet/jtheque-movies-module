package org.jtheque.movies.services.impl.parsers;

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

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.utils.StringUtils;

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
    public String getTitleKey() {
        return "movie.auto.parser.between.char";
    }

    @Override
    public Object[] getTitleReplaces() {
        return new Object[]{characterStart, characterEnd};
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
            name = StringUtils.delete(name, characterStart + cat.getTitle() + characterEnd);
        }

        return name;
    }
}