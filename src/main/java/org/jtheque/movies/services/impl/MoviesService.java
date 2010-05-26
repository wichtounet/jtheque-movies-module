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

import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.persistence.able.DataListener;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.collections.Filter;
import org.jtheque.utils.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A service for movies implementation.
 *
 * @author Baptiste Wicht
 */
public final class MoviesService implements IMoviesService {
    @Resource
    private IDaoMovies daoMovies;

    @Resource
    private ICategoriesService categoriesService;

    @Resource
    private IFFMpegService ffMpegService;

    @Resource
    private IMoviesModule moviesModule;

    @Override
    public Movie getEmptyMovie() {
        Movie movie = daoMovies.create();

        movie.setTitle("");

        return movie;
    }

    @Override
    public Collection<Movie> getMovies() {
        return daoMovies.getMovies();
    }

    @Override
    public Set<Movie> getMovies(Category category, boolean includeSubCategory) {
        Collection<Category> categories = new HashSet<Category>(10);

        categories.add(category);

        if (includeSubCategory) {
            categories.addAll(categoriesService.getSubCategories(category));
        }

        Set<Movie> movies = new HashSet<Movie>(getMovies());

        CollectionUtils.filter(movies, new CategoriesFilter(categories));

        return movies;
    }

    @Override
    @Transactional
    public boolean delete(Movie movie) {
        return daoMovies.delete(movie);
    }

    @Override
    @Transactional
    public void save(Movie movie) {
        daoMovies.save(movie);
    }

    @Override
    @Transactional
    public void create(Movie movie) {
        daoMovies.create(movie);
    }

    @Override
    public void clean(Movie movie, Collection<NameCleaner> cleaners) {
        clean(Arrays.asList(movie), cleaners);
    }

    @Override
    public void clean(Collection<Movie> movies, Collection<NameCleaner> cleaners) {
        for (Movie movie : movies) {
            String title = movie.getTitle();

            for (NameCleaner cleaner : cleaners) {
                title = cleaner.clearName(movie, title);
            }

            title = title.trim();

            if (!title.equals(movie.getTitle())) {
                movie.setTitle(title);
                save(movie);
            }
        }
    }

    @Override
    public boolean fileExists(String file) {
        for (Movie movie : getMovies()) {
            if (file.equals(movie.getFile())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean fileExistsInOtherMovie(Movie movie, String file) {
        for (Movie other : getMovies()) {
            if (movie.getId() != other.getId() && file.equals(other.getFile())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void saveImage(Movie movie, BufferedImage image) {
        String folder = moviesModule.getThumbnailFolderPath();

        String imageName = FileUtils.getFreeName(folder, movie.getTitle() + ".png");

        try {
            ImageIO.write(image, "png", new File(folder + imageName));
            
            movie.setImage(imageName);
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @Override
    public void fillInformations(Set<Movie> movies, boolean duration, boolean resolution, boolean image) {
        assert resolution || duration || image : "This method must be called with one of duration, resolution or image";

        for (Movie movie : movies) {
            if (new File(movie.getFile()).exists()) {
                generateInfos(duration, resolution, image, movie);

                save(movie);
            }
        }
    }

    @Override
    public Movie getMovie(String title) {
        return daoMovies.getMovie(title);
    }

    @Override
    public boolean thumbnailIsNotUsed(String name) {
        for(Movie movie : getMovies()){
            if(name.equals(movie.getImage())){
                return false;
            }
        }

        return true;
    }

    @Override
    public Collection<? extends Movie> getMoviesWithInvalidFiles() {
        Collection<Movie> movies = new ArrayList<Movie>(getMovies());

        CollectionUtils.filter(movies, new InvalidFileFilter());

        return movies;
    }

    /**
     * Generate the informations of the movie.
     *
     * @param duration   A boolean tag indicating if we must fill the duration (true) or not (false).
     * @param resolution A boolean tag indicating if we must fill the resolution (true) or not (false).
     * @param image      A boolean tag indicating if we must fill the image (true) or not (false).
     * @param movie      The movie to fill.
     */
    private void generateInfos(boolean duration, boolean resolution, boolean image, Movie movie) {
        File f = new File(movie.getFile());

        if (duration) {
            movie.setDuration(ffMpegService.getDuration(f));
        }

        if (resolution) {
            movie.setResolution(ffMpegService.getResolution(f));
        }

        if (image) {
            saveImage(movie, ffMpegService.generateRandomPreviewImage(f));
        }
    }

    @Override
    public Collection<Movie> getDatas() {
        return getMovies();
    }

    @Override
    public void addDataListener(DataListener listener) {
        daoMovies.addDataListener(listener);
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }

    @Override
    @Transactional
    public void clearAll(){
        daoMovies.clearAll();
    }

    /**
     * A collection filter to keep only movies with invalid files.
     *
     * @author Baptiste Wicht
     */
    private static final class InvalidFileFilter implements Filter<Movie> {
        @Override
        public boolean accept(Movie movie) {
            return movie.getFile() == null || !new File(movie.getFile()).exists();
        }
    }

    /**
     * A collection to keep only movies with categories in the specified set.
     *
     * @author Baptiste Wicht
     */
    private static final class CategoriesFilter implements Filter<Movie> {
        private final Collection<Category> categories;

        /**
         * Construct a new CategoriesFilter.
         *
         * @param categories The categories to filter with. 
         */
        private CategoriesFilter(Collection<Category> categories) {
            super();

            this.categories = categories;
        }

        @Override
        public boolean accept(Movie movie) {
            for(Category cat : categories){
                if(movie.isOfCategory(cat)){
                    return true;
                }
            }

            return false;
        }
    }
}