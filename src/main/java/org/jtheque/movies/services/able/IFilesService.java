package org.jtheque.movies.services.able;

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.parsers.FileParser;

import java.util.Collection;
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
 * A files service specification.
 *
 * @author Baptiste Wicht
 */
public interface IFilesService {
    /**
     * Create a movie based on the file path and the specified parsers. 
     * 
     * @param filePath The path to the file of the movie. 
     * @param parsers The parsers. 
     * 
     * @return The created Movie. 
     */
    Movie createMovie(String filePath, Collection<FileParser> parsers);

    /**
     * Return all the files who are movies on the specified folder. 
     * 
     * @param folder The folder to read. 
     * 
     * @return All the files of the specified folder. 
     */
    Collection<File> getMovieFiles(File folder);

    /**
     * Import all the movies from the specified files. 
     * 
     * @param files The files to import movies from. 
     * @param parsers The parsers to use. 
     */
    void importMovies(Collection<File> files, Collection<FileParser> parsers);
}