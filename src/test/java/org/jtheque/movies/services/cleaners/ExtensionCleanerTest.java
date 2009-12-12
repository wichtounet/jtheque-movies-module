package org.jtheque.movies.services.cleaners;

import org.jtheque.movies.services.impl.cleaners.ExtensionCleaner;
import org.jtheque.movies.persistence.od.impl.MovieImpl;

import static org.junit.Assert.*;
import org.junit.Test;

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
 * @author Baptiste Wicht
 */
public class ExtensionCleanerTest {
    @Test
    public void clearName(){
        ExtensionCleaner cleaner = new ExtensionCleaner();
        
        assertEquals(cleaner.clearName(new MovieImpl(), "super fichier.wma"), "super fichier");
    }
}