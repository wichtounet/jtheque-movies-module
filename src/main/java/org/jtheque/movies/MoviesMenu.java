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
import org.jtheque.primary.able.controller.IChoiceController;
import org.jtheque.ui.able.IController;
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
        IController choiceController = getService(IChoiceController.class);
        IController categoryController = getBean("categoryController");
        IController filesController = getBean("filesController");
        IController importController = getBean("importController");
        IController generateController = getBean("generateController");
        IController moviesController = getBean("moviesController");

        return features(
                createMainFeature(500, "category.menu.title",
                        createSubFeature(1, createControllerAction("category.actions.new", categoryController)),
                        createSubFeature(2, createControllerAction("category.actions.edit", choiceController)),
                        createSubFeature(3, createControllerAction("category.actions.delete", choiceController)),
                        createSeparatedSubFeature(100, createControllerAction("movie.auto.folder.actions.add", importController))
                ),
                createMainFeature(400, "movie.menu.title",
                        createSubFeature(100, createControllerAction("movie.actions.clean.category", choiceController)),
                        createSubFeature(101, createControllerAction("movie.generate.infos", generateController)),
                        createSubFeature(102, createControllerAction("movie.files", filesController)),
                        createSubFeature(1, createControllerAction("movie.actions.clean.thumbnails", moviesController))
                ));
    }
}