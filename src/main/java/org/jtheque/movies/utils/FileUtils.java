package org.jtheque.movies.utils;

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
 * A temporary file utility class.
 *
 * @author Baptiste Wicht
 */
public final class FileUtils {
    /**
     * Utility class, not instanciable. 
     */
    private FileUtils() {
        super();
    }

    /**
     * Return an existing file.
     *
     * @return An existing file object. 
     */
    public static File getAnExistingFile(){
        for(File root : File.listRoots()){
            for(File f : root.listFiles()){
                if(f.isFile() && f.length() > 0){
                    return f;
                }
            }
        }

        return null;
    }
}