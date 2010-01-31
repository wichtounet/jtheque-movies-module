package org.jtheque.movies.services.impl;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.services.able.IFilesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.utils.collections.CollectionUtils;

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
            Managers.getManager(IViewManager.class).displayError(new InternationalizedError("movie.errors.filenotcreated"));
        }

    }

    @Override
    public Movie createMovie(String filePath, Collection<FileParser> parsers) {
        Movie movie = moviesService.getEmptyMovie();

        movie.setNote(DaoNotes.getInstance().getNote(DaoNotes.NoteType.UNDEFINED));
        movie.setFile(filePath);

        File file = new File(filePath);

        movie.setResolution(ffMpegService.getResolution(file));
        movie.setDuration(ffMpegService.getDuration(file));

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
        if (folder.isDirectory()) {
            Collection<File> files = new ArrayList<File>(50);

            readFolder(folder, files);

            return files;
        } else {
            return CollectionUtils.emptyList();
        }
    }

    /**
     * Read the folder and all the files of the folder in the collection.
     *
     * @param folder The folder to read.
     * @param files  The collection to add the files to.
     */
    private static void readFolder(File folder, Collection<File> files) {
        for (File file : folder.listFiles(new MovieFileNameFilter())) {
            if (file.isDirectory()) {
                readFolder(file, files);
            } else {
                files.add(file);
            }
        }
    }
}