package org.jtheque.movies.services.impl;

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

import org.jtheque.errors.able.IErrorService;
import org.jtheque.errors.utils.Errors;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.services.able.IFilesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.persistence.able.IDaoNotes;
import org.jtheque.persistence.impl.DaoNotes;
import org.jtheque.utils.io.FileUtils;

import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A files service implementation.
 *
 * @author Baptiste Wicht
 */
public final class FilesService implements IFilesService {
    @Resource
    private IMoviesService moviesService;

    @Resource
    private ICategoriesService categoriesService;

    @Resource
    private IFFMpegService ffMpegService;

    @Resource
    private IErrorService errorService;

    @Resource
    private IDaoNotes daoNotes;

    @Override
    public void importMovies(Collection<File> files, Collection<FileParser> parsers) {
        assert !files.isEmpty() : "Files cannot be empty";

        boolean fileNotCreated = false;

        for (File f : files) {
            if (moviesService.fileExists(f.getAbsolutePath())) {
                fileNotCreated = true;
            } else {
                createMovie(f.getAbsolutePath(), parsers);
            }
        }

        if (fileNotCreated) {
            errorService.addError(Errors.newI18nError("movie.errors.filenotcreated"));
        }
    }

    @Override
    public Movie createMovie(String filePath, Collection<FileParser> parsers) {
        Movie movie = moviesService.getEmptyMovie();

        movie.setNote(daoNotes.getNote(DaoNotes.NoteType.UNDEFINED));
        movie.setFile(filePath);

        File file = new File(filePath);

        if (ffMpegService.ffmpegIsInstalled()) {
            movie.setResolution(ffMpegService.getResolution(file));
            movie.setDuration(ffMpegService.getDuration(file));

            moviesService.saveImage(movie, ffMpegService.generateRandomPreviewImage(file));
        }

        extractCategoriesAndTitle(filePath, parsers, movie);

        moviesService.create(movie);

        return movie;
    }

    /**
     * Extract the categories and the title from the file.
     *
     * @param filePath The path to the file.
     * @param parsers  The parsers to use.
     * @param movie    The movie to fill.
     */
    private void extractCategoriesAndTitle(String filePath, Iterable<FileParser> parsers, Movie movie) {
        File file = new File(filePath);

        String title = file.getName();

        Collection<Category> categories = new ArrayList<Category>(5);

        for (FileParser parser : parsers) {
            parser.parseFilePath(file);
            categories.addAll(parser.getExtractedCategories());
            title = parser.clearFileName(title);
        }

        movie.addCategories(categories);
        movie.setTitle(title);

        createUnsavedCategories(categories);
    }

    /**
     * Create all the unsaved categories of the collection.
     *
     * @param categories A collection of categories.
     */
    private void createUnsavedCategories(Iterable<Category> categories) {
        for (Category category : categories) {
            if (!category.isSaved()) {
                categoriesService.create(category);
            }
        }
    }

    @Override
    public Collection<File> getMovieFiles(File folder) {
        return FileUtils.getFilesOfFolder(folder, new MovieFileNameFilter());
    }
}