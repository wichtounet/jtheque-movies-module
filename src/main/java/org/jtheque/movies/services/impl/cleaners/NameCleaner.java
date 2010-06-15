package org.jtheque.movies.services.impl.cleaners;

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

import org.jtheque.movies.persistence.od.able.Movie;

/**
 * An object to clean the name of a movie.
 *
 * @author Baptiste Wicht
 */
public interface NameCleaner {
    /**
     * Return the internationalized title of the cleaner.
     *
     * @return The internationalized title of the cleaner.
     */
    String getTitleKey();

    Object[] getTitleReplaces();

    /**
     * Clear the name.
     *
     * @param name  The name to clear.
     * @param movie The movie to clean the name for.
     *
     * @return the cleared name.
     */
    String clearName(Movie movie, String name);
}