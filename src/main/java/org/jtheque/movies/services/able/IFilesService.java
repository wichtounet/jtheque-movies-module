package org.jtheque.movies.services.able;

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.parsers.FileParser;

import java.io.File;
import java.util.Collection;

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
 * A files service specification.
 *
 * @author Baptiste Wicht
 */
public interface IFilesService {
    /**
     * Create a movie based on the file path and the specified parsers.
     *
     * @param filePath The path to the file of the movie.
     * @param parsers  The parsers.
     *
     * @return The created Movie.
     */
    Movie createMovie(String filePath, Collection<FileParser> parsers);

    /**
     * Return all the files who are movies on the specified folder. The search is made in a recursive way. It seems that
     * the sub folders are also taken.
     *
     * @param folder The folder to read.
     *
     * @return All the files of the specified folder.
     */
    Collection<File> getMovieFiles(File folder);

    /**
     * Import all the movies from the specified files.
     *
     * @param files   The files to import movies from.
     * @param parsers The parsers to use.
     */
    void importMovies(Collection<File> files, Collection<FileParser> parsers);
}