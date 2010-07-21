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

import org.jtheque.collections.able.ICollectionsService;
import org.jtheque.core.able.ICore;
import org.jtheque.modules.utils.SwingModule;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.primary.able.IPrimaryUtils;
import org.jtheque.primary.able.controller.IChoiceController;
import org.jtheque.primary.utils.DataTypeManager;
import org.jtheque.states.able.IStateService;
import org.jtheque.utils.io.FileUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import java.io.File;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;

/**
 * A JTheque Module for managing movies.
 *
 * @author Baptiste Wicht
 */
public final class MoviesModule extends SwingModule implements IMoviesModule {
    private IMovieConfiguration config;

    private String thumbnailFolderPath;

    @Resource
    private IStateService stateService;

    @Resource
    private IPrimaryUtils primaryUtils;

    @Resource
    private IChoiceController choiceController;

    @Resource
    private ICore core;

    public MoviesModule() {
        super("org.jtheque.movies", new String[]{"panelConfigMovies"});
    }

    /**
     * Pre plug the module.
     */
    @PostConstruct
    public void plug() {
        primaryUtils.setPrimaryImpl("Movies");

        config = stateService.getState(new MovieConfiguration());

        choiceController.registerAction("category.actions.edit", "edit", ICategoriesService.DATA_TYPE);
        choiceController.registerAction("category.actions.delete", "delete", ICategoriesService.DATA_TYPE);
        choiceController.registerAction("movie.actions.clean.category", "clean", ICategoriesService.DATA_TYPE);
        
        DataTypeManager.bindDataTypeToKey(ICollectionsService.DATA_TYPE, "data.titles.collection");
        DataTypeManager.bindDataTypeToKey(ICategoriesService.DATA_TYPE, "data.titles.category");
        DataTypeManager.bindDataTypeToKey(IMoviesService.DATA_TYPE, "data.titles.movie");

        NativeInterface.open();
    }

    /**
     * Unplug the module.
     */
    @PreDestroy
    public void unplug() {
        DataTypeManager.unbindDataType(ICategoriesService.DATA_TYPE);
        DataTypeManager.unbindDataType(IMoviesService.DATA_TYPE);
        DataTypeManager.unbindDataType(ICollectionsService.DATA_TYPE);

        NativeInterface.runEventPump();
    }

    @Override
    public IMovieConfiguration getConfig() {
        return config;
    }

    @Override
    public String getThumbnailFolderPath() {
        if (thumbnailFolderPath == null) {
            File thumbnailFolder = new File(core.getFolders().getApplicationFolder(), "/thumbnails");

            FileUtils.createIfNotExists(thumbnailFolder);

            thumbnailFolderPath = thumbnailFolder.getAbsolutePath() + '/';
        }

        return thumbnailFolderPath;
    }
}
