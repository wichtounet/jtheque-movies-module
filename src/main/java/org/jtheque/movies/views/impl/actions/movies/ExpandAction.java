package org.jtheque.movies.views.impl.actions.movies;

import org.jtheque.images.able.IImageService;
import org.jtheque.movies.MoviesResources;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.ui.utils.actions.JThequeSimpleAction;

import java.awt.event.ActionEvent;

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
 * Action to expand the tree.
 *
 * @author Baptiste Wicht
 */
public final class ExpandAction extends JThequeSimpleAction {
    private final IMovieView movieView;

    /**
     * Construct a new ExpandAction.
     *
     * @param movieView The movie view.
     * @param imageService The image service.
     */
    public ExpandAction(IMovieView movieView, IImageService imageService) {
        super();

        this.movieView = movieView;

        setIcon(imageService.getIcon(MoviesResources.EXPAND_ICON));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movieView.expandAll();
    }
}
