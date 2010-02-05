package org.jtheque.movies.services.impl.cleaners;

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
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.utils.StringUtils;

/**
 * @author Baptiste Wicht
 */
public final class CategoryNameCleaner implements NameCleaner {
    @Override
    public String getTitle() {
        return CoreUtils.getMessage("movie.clean.cleaner.category");
    }

    @Override
    public String clearName(Movie movie, String name) {
        return StringUtils.delete(name, movie.getCategories().toArray());
    }
}