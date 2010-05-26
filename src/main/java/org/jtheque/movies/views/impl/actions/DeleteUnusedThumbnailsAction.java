package org.jtheque.movies.views.impl.actions;

import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.ui.utils.actions.JThequeAction;

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
 * Action to delete the unused thumbnails file.
 *
 * @author Baptiste Wicht
 */
public final class DeleteUnusedThumbnailsAction extends JThequeAction {
	private final IMoviesModule moviesModule;
	private final IMoviesService moviesService;

    /**
     * Construct a new DeleteUnusedThumbnailsAction.
     *
     * @param moviesModule
     * @param moviesService
     */
    public DeleteUnusedThumbnailsAction(IMoviesModule moviesModule, IMoviesService moviesService) {
        super("movie.actions.clean.thumbnails");
	    
	    this.moviesModule = moviesModule;
	    this.moviesService = moviesService;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        File folder = new File(moviesModule.getThumbnailFolderPath());

        for(File f : folder.listFiles()){
            String name = f.getName();

            if(moviesService.thumbnailIsNotUsed(name)){
                boolean delete = f.delete();

                if(!delete){
                    f.deleteOnExit();
                }
            }
        }
    }
}