package org.jtheque.movies.services.impl.cleaners;

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.utils.StringUtils;

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

/**
 * @author Baptiste Wicht
 */
public final class NumberCleaner implements NameCleaner {
    private static final Object[] REPLACES = new Object[0];

    @Override
    public String getTitleKey() {
        return "movie.clean.cleaner.number";
    }

    @Override
    public Object[] getTitleReplaces() {
        return REPLACES;
    }

    @Override
    public String clearName(Movie movie, String name) {
        return StringUtils.removeNumbers(name);
    }
}
