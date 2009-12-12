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

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;

/**
 * An object to clean the extension from a movie's name. 
 * 
 * @author Baptiste Wicht
 */
public final class ExtensionCleaner implements NameCleaner{
    @Override
    public String getTitle() {
        return Managers.getManager(ILanguageManager.class).getMessage("movie.clen.cleaner.extension");
    }

    @Override
    public String clearName(Movie movie, String name) {
        String cleared = name;
        
        if(cleared.contains(".")){
            cleared = cleared.substring(0, cleared.lastIndexOf('.'));
        }
        
        return cleared;
    }
}