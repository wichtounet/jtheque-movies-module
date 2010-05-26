package org.jtheque.movies;

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

import org.jtheque.features.able.IFeature;
import org.jtheque.movies.controllers.able.ICategoryController;
import org.jtheque.movies.controllers.able.IFilesController;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IGenerateInfosView;
import org.jtheque.movies.views.able.IImportFolderView;
import org.jtheque.movies.views.impl.actions.DeleteUnusedThumbnailsAction;
import org.jtheque.movies.views.impl.actions.categories.CreateNewCategoryAction;
import org.jtheque.movies.views.impl.actions.files.DisplayFilesViewAction;
import org.jtheque.primary.able.controller.IChoiceController;
import org.jtheque.primary.utils.choice.ChoiceViewAction;
import org.jtheque.views.utils.OSGIMenu;

import java.util.List;

/**
 * A menu for the Movies Module.
 *
 * @author Baptiste Wicht
 */
public final class MoviesMenu extends OSGIMenu {
    @Override
    protected List<IFeature> getMenuMainFeatures() {
        IMoviesModule moviesModule = getService(IMoviesModule.class);
        ICategoryController categoryController = getService(ICategoryController.class);
        IChoiceController choiceController = getService(IChoiceController.class);
		IFilesController filesController = getService(IFilesController.class);
        IMoviesService moviesService = getService(IMoviesService.class);
        IGenerateInfosView generateInfosView = getService(IGenerateInfosView.class);
        IImportFolderView importFolderView = getService(IImportFolderView.class);
	    
        return features(
                createMainFeature(500, "category.menu.title",
                        createSubFeature(1, new CreateNewCategoryAction(categoryController)),
                        createSubFeature(2, new ChoiceViewAction("category.actions.edit", "edit", ICategoriesService.DATA_TYPE, choiceController)),
                        createSubFeature(3, new ChoiceViewAction("category.actions.delete", "delete", ICategoriesService.DATA_TYPE, choiceController)),
                        createSeparatedSubFeature(100, createDisplayViewAction("movie.auto.folder.actions.add", importFolderView))
                ),
                createMainFeature(400, "movie.menu.title",
                        createSubFeature(100, new ChoiceViewAction("movie.actions.clean.category", "clean", ICategoriesService.DATA_TYPE, choiceController)),
                        createSubFeature(101, createDisplayViewAction("movie.generate.infos", generateInfosView)),
                        createSubFeature(102, new DisplayFilesViewAction(filesController)),
                        createSubFeature(1, new DeleteUnusedThumbnailsAction(moviesModule, moviesService))
                ));
    }
}