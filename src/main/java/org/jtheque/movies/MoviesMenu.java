package org.jtheque.movies;

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

import org.jtheque.core.managers.feature.AbstractMenu;
import org.jtheque.core.managers.feature.Feature;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.views.impl.actions.categories.AcNewCategory;
import org.jtheque.primary.view.impl.actions.choice.ChoiceViewAction;

import java.util.List;

public final class MoviesMenu extends AbstractMenu {
    @Override
    protected List<Feature> getMenuMainFeatures(){
        return features(
                createMainFeature(500, "category.menu.title",
                    createSubFeature(1, new AcNewCategory()),
                    createSubFeature(2, new ChoiceViewAction("category.actions.edit", "edit", ICategoriesService.DATA_TYPE)),
                    createSubFeature(3, new ChoiceViewAction("category.actions.delete", "delete", ICategoriesService.DATA_TYPE))
        ));
    }

    @Override
    protected List<Feature> getFileMenuSubFeatures(){
        return features(
                createSeparatedSubFeature(100, createDisplayViewAction("movie.auto.folder.actions.add", "importFolderView")),
                createSeparatedSubFeature(101, new ChoiceViewAction("movie.actions.clean.category", "clean", ICategoriesService.DATA_TYPE)));
    }
}