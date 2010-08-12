package org.jtheque.movies.views.impl.panel;

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

import org.jtheque.images.ImageService;
import org.jtheque.movies.MoviesResources;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.views.able.ICategoriesView;
import org.jtheque.movies.views.able.ICategoryView;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.models.CategoriesListModel;
import org.jtheque.primary.able.controller.IChoiceController;
import org.jtheque.ui.Controller;
import org.jtheque.ui.components.Components;
import org.jtheque.ui.utils.ValidationUtils;
import org.jtheque.ui.utils.actions.ActionFactory;
import org.jtheque.ui.utils.actions.JThequeSimpleAction;
import org.jtheque.ui.utils.builded.OSGIFilthyBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.models.SimpleListModel;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.Resource;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collection;

/**
 * A panel to display the categories of a movie and enable the user to edit them.
 *
 * @author Baptiste Wicht
 */
public final class JPanelCategories extends OSGIFilthyBuildedPanel implements ICategoriesView {
    private JList listCategories;
    private JList listCategoriesForFilm;

    private CategoriesListModel categoriesModel;
    private SimpleListModel<Category> categoriesForMovieModel;

    @Resource
    private ICategoriesService categoriesService;

    @Resource
    private IChoiceController choiceController;

    @Resource
    private Controller<ICategoryView> categoryController;

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        categoriesModel = new CategoriesListModel(categoriesService);

        double anHalf = 0.5;

        ListCellRenderer renderer = Components.newIconListRenderer(
                getService(ImageService.class).getIcon(MoviesResources.BOX_ICON));

        listCategories = builder.addScrolledList(categoriesModel, renderer,
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, anHalf, 1.0));

        PanelBuilder buttons = builder.addPanel(builder.gbcSet(1, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, -1, 1));

        buttons.addButton(new AcAddToList(), builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        buttons.addButton(new AcRemoveFromList(), builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));

        categoriesForMovieModel = new SimpleListModel<Category>();
        categoriesModel.setLinkedModel(categoriesForMovieModel);

        listCategoriesForFilm = builder.addScrolledList(categoriesForMovieModel, renderer,
                builder.gbcSet(2, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, -1, anHalf, 1.0));

        I18nPanelBuilder manageButtons = builder.addPanel(builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 0));

        manageButtons.addI18nLabel("category.view.manage", Font.BOLD, builder.gbcSet(0, 0));
        manageButtons.addButton(ActionFactory.createAction("category.actions.new", categoryController), builder.gbcSet(1, 0));
        manageButtons.addButton(ActionFactory.createAction("category.actions.edit", choiceController), builder.gbcSet(2, 0));
        manageButtons.addButton(ActionFactory.createAction("category.actions.delete", choiceController), builder.gbcSet(3, 0));
    }

    @Override
    public void fillFilm(IMovieFormBean fb) {
        if (categoriesForMovieModel.getSize() != 0) {
            fb.setCategories(categoriesForMovieModel.getObjects());
        }
    }

    @Override
    public void reload(Movie movie) {
        categoriesForMovieModel.clear();
        categoriesModel.reload();

        for (Category category : movie.getCategories()) {
            categoriesModel.removeElement(category);
            categoriesForMovieModel.addElement(category);
        }
    }

    @Override
    public void validate(Collection<org.jtheque.errors.Error> errors) {
        ValidationUtils.rejectIfEmpty(listCategoriesForFilm, "movie.categories", errors);
    }

    /**
     * Action to add an actor to the list.
     *
     * @author Baptiste Wicht
     */
    private final class AcAddToList extends JThequeSimpleAction {
        private static final long serialVersionUID = -330415352827742843L;

        /**
         * Construct a new AcAddToList action with a text ">>".
         */
        private AcAddToList() {
            super();

            setText(" >> ");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selectedActors = listCategories.getSelectedIndices();

            Arrays.sort(selectedActors);

            ArrayUtils.reverse(selectedActors);

            for (int index : selectedActors) {
                Category actor = categoriesModel.removeElement(index);
                categoriesForMovieModel.addElement(actor);
            }
        }
    }

    /**
     * Action to remove an actor from the list.
     *
     * @author Baptiste Wicht
     */
    private final class AcRemoveFromList extends JThequeSimpleAction {
        private static final long serialVersionUID = 4590966825608130118L;

        /**
         * Construct a new AcRemoveFromList action with a text <<.
         */
        private AcRemoveFromList() {
            super();

            setText(" << ");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selectedActors = listCategoriesForFilm.getSelectedIndices();

            Arrays.sort(selectedActors);

            ArrayUtils.reverse(selectedActors);

            for (int index : listCategoriesForFilm.getSelectedIndices()) {
                Category actor = categoriesForMovieModel.removeElement(index);
                categoriesModel.addElement(actor);
            }
        }
    }
}
