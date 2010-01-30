package org.jtheque.movies.views.impl.actions.movies.image;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.controllers.able.IImageController;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.utils.StringUtils;

import java.awt.event.ActionEvent;
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

public final class GenerateRandomImageAction extends JThequeAction {
    /**
     * Construct a new GenerateRandomImageAction.
     */
    public GenerateRandomImageAction(){
        super("movie.image.actions.ffmpeg.random");
    }

	@Override
	public void actionPerformed(ActionEvent e){
		Movie movie = CoreUtils.<IMovieController>getBean("movieController").getViewModel().getCurrentMovie();

        File file = new File(movie.getFile());

        if(StringUtils.isNotEmpty(movie.getFile()) && file.exists()){
			CoreUtils.<IImageController>getBean("imageController").getView().setImage(
					CoreUtils.<IFFMpegService>getBean("ffmpegService").generateRandomPreviewImage(file));
        } else {
            Managers.getManager(IViewManager.class).displayError(new InternationalizedError("movie.errors.filenotfound"));
        }
	}
}