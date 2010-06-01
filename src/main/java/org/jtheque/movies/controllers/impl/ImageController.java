package org.jtheque.movies.controllers.impl;

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
import org.jtheque.movies.controllers.able.IImageController;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IImageView;
import org.jtheque.spring.utils.SwingSpringProxy;
import org.jtheque.utils.StringUtils;
import org.jtheque.views.utils.AbstractController;

import javax.annotation.Resource;

import java.io.File;

/**
 * Controller for the category view.
 *
 * @author Baptiste Wicht
 */
public final class ImageController extends AbstractController implements IImageController {
    private final SwingSpringProxy<IImageView> imageView;

    @Resource
    private IMovieController movieController;

    @Resource
    private IFFMpegService ffmpegService;

    @Resource
    private IMoviesService moviesService;

    public ImageController(SwingSpringProxy<IImageView> imageView) {
        super();

        this.imageView = imageView;
    }

    @Override
    public void editImage() {
        imageView.get().displayMovie(getCurrentMovie());
        imageView.get().display();
    }

    @Override
    public IImageView getView() {
        return imageView.get();
    }

    @Override
    public void generateFileImage(String imagePath) {
        File file = new File(imagePath);

        if (StringUtils.isNotEmpty(imagePath) && file.exists()) {
            imageView.get().setImage(ffmpegService.generateImageFromUserInput(file));
        } else {
            displayFileNotFoundError();
        }
    }

    @Override
    public void generateRandomImage() {
        Movie movie = getCurrentMovie();

        File file = new File(movie.getFile());

        if (StringUtils.isNotEmpty(movie.getFile()) && file.exists()) {
            imageView.get().setImage(ffmpegService.generateRandomPreviewImage(file));
        } else {
            displayFileNotFoundError();
        }
    }

    @Override
    public void generateTimeImage(String time) {
        Movie movie = getCurrentMovie();

        File file = new File(movie.getFile());

        if (StringUtils.isNotEmpty(movie.getFile()) && file.exists()) {
            imageView.get().setImage(ffmpegService.generatePreviewImage(file, time));
        } else {
            displayFileNotFoundError();
        }
    }

    @Override
    public void save() {
        moviesService.saveImage(getCurrentMovie(), imageView.get().getImage());

        imageView.get().closeDown();
    }

    /**
     * Return the current movie.
     *
     * @return The current movie.
     */
    private Movie getCurrentMovie() {
        return movieController.getViewModel().getCurrentMovie();
    }

    /**
     * Display a file not found error.
     */
    private void displayFileNotFoundError() {
        getService(IErrorService.class).addInternationalizedError("movie.errors.filenotfound");
    }
}