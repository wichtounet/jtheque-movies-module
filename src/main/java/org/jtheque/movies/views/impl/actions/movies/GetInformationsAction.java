package org.jtheque.movies.views.impl.actions.movies;

import org.jtheque.errors.able.IErrorService;
import org.jtheque.errors.utils.Errors;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.views.impl.panel.EditMoviePanel;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.StringUtils;

import java.awt.event.ActionEvent;
import java.io.File;

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
 * An action to get informations about a movie from its file.
 *
 * @author Baptiste Wicht
 */
public final class GetInformationsAction extends JThequeAction {
    private final EditMoviePanel editMoviePanel;
    private final IFFMpegService ffMpegService;
    private final IErrorService errorService;

    /**
     * Construct a new GetInformationsAction.
     *
     * @param editMoviePanel The edit movie panel.
     * @param ffMpegService  The ffmpeg service.
     * @param errorService   The error service.
     */
    public GetInformationsAction(EditMoviePanel editMoviePanel, IFFMpegService ffMpegService, IErrorService errorService) {
        super("movie.actions.infos");

        this.editMoviePanel = editMoviePanel;
        this.ffMpegService = ffMpegService;
        this.errorService = errorService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String filePath = editMoviePanel.getFilePath();

        File file = new File(filePath);

        if (StringUtils.isNotEmpty(filePath) && file.exists()) {
            editMoviePanel.setResolution(ffMpegService.getResolution(file));
            editMoviePanel.setDuration(ffMpegService.getDuration(file));
        } else {
            errorService.addError(Errors.newI18nError("movie.errors.filenotfound"));
        }
    }
}