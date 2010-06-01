package org.jtheque.movies.views.impl.actions.movies.image;

import org.jtheque.movies.controllers.able.IImageController;
import org.jtheque.movies.views.able.IImageView;
import org.jtheque.ui.utils.actions.JThequeAction;

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
 * An action to generate an image from a file of the user.
 *
 * @author Baptiste Wicht
 */
public final class GenerateFileImageAction extends JThequeAction {
    private final IImageController imageController;

    /**
     * Construct a new GenerateFileImageAction.
     *
     * @param imageController The image controller.
     */
    public GenerateFileImageAction(IImageController imageController) {
        super("movie.image.actions.file");

        this.imageController = imageController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IImageView view = imageController.getView();

        imageController.generateFileImage(view.getImagePath());
    }
}