package org.jtheque.movies.views.impl.actions;

import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.services.able.IMoviesService;

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

public class DeleteUnusedThumbnailsAction extends JThequeAction {
    public DeleteUnusedThumbnailsAction() {
        super("movie.actions.clean.thumbnails");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        IMoviesModule module = CoreUtils.getBean("moviesModule");

        IMoviesService service = CoreUtils.getBean("moviesService");

        File folder = new File(module.getThumbnailFolderPath());

        for(File f : folder.listFiles()){
            String name = f.getName();

            if(service.thumbnailIsNotUsed(name)){
                boolean delete = f.delete();

                if(!delete){
                    f.deleteOnExit();
                }
            }
        }
    }
}