package org.jtheque.movies.controllers.impl;

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
import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.movies.controllers.able.IImageController;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IImageView;
import org.jtheque.utils.StringUtils;

import javax.annotation.Resource;
import java.io.File;

/**
 * Controller for the category view.
 *
 * @author Baptiste Wicht
 */
public final class ImageController extends AbstractController implements IImageController {
    @Resource
    private IImageView imageView;

    @Resource
    private IMovieController movieController;

    @Resource
    private IFFMpegService ffmpegService;

    @Resource
    private IMoviesService moviesService;

    @Override
    public void editImage() {
        imageView.displayMovie(getCurrentMovie());
        imageView.display();
    }

    @Override
    public IImageView getView() {
        return imageView;
    }

    @Override
    public void generateFileImage(String imagePath) {
        File file = new File(imagePath);

        if (StringUtils.isNotEmpty(imagePath) && file.exists()) {
            imageView.setImage(ffmpegService.generateImageFromUserInput(file));
        } else {
            displayFileNotFoundError();
        }
    }

    @Override
    public void generateRandomImage() {
        Movie movie = getCurrentMovie();

        File file = new File(movie.getFile());

        if (StringUtils.isNotEmpty(movie.getFile()) && file.exists()) {
            imageView.setImage(ffmpegService.generateRandomPreviewImage(file));
        } else {
            displayFileNotFoundError();
        }
    }

    @Override
    public void generateTimeImage(String time) {
        Movie movie = getCurrentMovie();

        File file = new File(movie.getFile());

        if (StringUtils.isNotEmpty(movie.getFile()) && file.exists()) {
            imageView.setImage(ffmpegService.generatePreviewImage(file, time));
        } else {
            displayFileNotFoundError();
        }
    }

    @Override
    public void save() {
        moviesService.saveImage(getCurrentMovie(), imageView.getImage());

        imageView.closeDown();
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
    private static void displayFileNotFoundError() {
        Managers.getManager(IViewManager.class).displayError(new InternationalizedError("movie.errors.filenotfound"));
    }
}