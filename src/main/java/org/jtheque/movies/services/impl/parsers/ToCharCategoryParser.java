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
    public String getTitleKey() {
        return "movie.auto.parser.to.char";
    }

    @Override
    public Object[] getTitleReplaces() {
        return new Object[]{"-"};
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