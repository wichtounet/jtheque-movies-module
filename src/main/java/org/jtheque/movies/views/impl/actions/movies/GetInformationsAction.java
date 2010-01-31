package org.jtheque.movies.views.impl.actions.movies;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.views.impl.panel.EditMoviePanel;
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

/**
 * An action to get informations about a movie from its file.
 *
 * @author Baptiste Wicht
 */
public final class GetInformationsAction extends JThequeAction {
    private final EditMoviePanel editMoviePanel;

    /**
     * Construct a new GetInformationsAction.
     *
     * @param editMoviePanel The edit movie panel.
     */
    public GetInformationsAction(EditMoviePanel editMoviePanel) {
        super("movie.actions.infos");

        this.editMoviePanel = editMoviePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String filePath = editMoviePanel.getFilePath();

        File file = new File(filePath);

        if (StringUtils.isNotEmpty(filePath) && file.exists()) {
            editMoviePanel.setResolution(CoreUtils.<IFFMpegService>getBean("ffmpegService").getResolution(file));
            editMoviePanel.setDuration(CoreUtils.<IFFMpegService>getBean("ffmpegService").getDuration(file));
        } else {
            Managers.getManager(IViewManager.class).displayError(new InternationalizedError("movie.errors.filenotfound"));
        }
    }
}