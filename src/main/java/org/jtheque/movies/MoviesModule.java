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

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import org.jtheque.collections.able.ICollectionsService;
import org.jtheque.core.able.ICore;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.IOpeningConfigView;
import org.jtheque.movies.views.impl.JPanelConfigMovies;
import org.jtheque.primary.able.IPrimaryUtils;
import org.jtheque.primary.utils.DataTypeManager;
import org.jtheque.spring.utils.SwingSpringProxy;
import org.jtheque.states.able.IStateService;
import org.jtheque.utils.io.FileUtils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.File;

/**
 * A JTheque Module for managing movies.
 *
 * @author Baptiste Wicht
 */
public final class MoviesModule implements IMoviesModule, ApplicationContextAware {
    private IMovieConfiguration config;

    private String thumbnailFolderPath;

    @Resource
    private IStateService stateService;

    @Resource
	private IPrimaryUtils primaryUtils;

    @Resource
	private ICore core;

    /**
     * Pre plug the module.
     */
    @PostConstruct
    public void plug() {
        primaryUtils.setPrimaryImpl("Movies");
        primaryUtils.prePlug();

        primaryUtils.plug();

        config = stateService.getState(new MovieConfiguration());

        DataTypeManager.bindDataTypeToKey(ICollectionsService.DATA_TYPE, "data.titles.collection");
        DataTypeManager.bindDataTypeToKey(ICategoriesService.DATA_TYPE, "data.titles.category");
        DataTypeManager.bindDataTypeToKey(IMoviesService.DATA_TYPE, "data.titles.movie");

        NativeInterface.open();
    }

    /**
     * Unplug the module.
     */
    @PreDestroy
    private void unplug() {
        DataTypeManager.unbindDataType(ICategoriesService.DATA_TYPE);
        DataTypeManager.unbindDataType(IMoviesService.DATA_TYPE);
        DataTypeManager.unbindDataType(ICollectionsService.DATA_TYPE);

        primaryUtils.unplug();

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        new SwingSpringProxy<IMovieView>(IMovieView.class, applicationContext).get();
        new SwingSpringProxy<IOpeningConfigView>(IOpeningConfigView.class, applicationContext).get();
    }
}
