package org.jtheque.movies.views.impl.frames;

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

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.utils.SwingUtils;
import org.jtheque.movies.views.able.IGenerateInfosView;
import org.jtheque.movies.views.impl.actions.generate.ValidateGenerateInfosViewAction;
import org.jtheque.movies.views.impl.panel.FilthyRenderer;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JCheckBox;

/**
 * A view implementation to select the options to clean the name of movies.
 *
 * @author Baptiste Wicht
 */
public final class GenerateInfosView extends SwingFilthyBuildedDialogView<IModel> implements IGenerateInfosView {
    private DataContainerCachedComboBoxModel<Category> categoriesModel;

    private JCheckBox checkBoxDuration;
    private JCheckBox checkBoxResolution;
    private JCheckBox checkBoxImage;
    private JCheckBox checkBoxSub;

    /**
     * Construct a new CleanView.
     */
    public GenerateInfosView() {
        super();

        build();
    }

    @Override
    protected void initView() {
        setTitleKey("movie.generate.title");
        setResizable(false);
    }

    @Override
    protected void buildView(PanelBuilder builder) {
        builder.addI18nLabel("data.titles.category", builder.gbcSet(0, 0));

        categoriesModel = new DataContainerCachedComboBoxModel<Category>(
                SwingDialogView.<DataContainer<Category>>getBean("categoriesService"));

        builder.addComboBox(categoriesModel, new FilthyRenderer(), builder.gbcSet(1, 0));

        checkBoxDuration = SwingUtils.addFilthyCheckbox(builder, "movie.infos.duration", builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, 2, 1));
        checkBoxResolution = SwingUtils.addFilthyCheckbox(builder, "movie.infos.resolution", builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, 2, 1));
        checkBoxImage = SwingUtils.addFilthyCheckbox(builder, "movie.infos.image", builder.gbcSet(0, 3, GridBagUtils.HORIZONTAL, 2, 1));
        checkBoxSub = SwingUtils.addFilthyCheckbox(builder, "movie.clean.subcategories", builder.gbcSet(0, 4, GridBagUtils.HORIZONTAL, 2, 1));

        SwingUtils.addFilthyButtonBar(builder, builder.gbcSet(0, 5, GridBagUtils.HORIZONTAL, 2, 1),
                new ValidateGenerateInfosViewAction(), getCloseAction("movie.auto.actions.cancel"));
    }

    @Override
    public Category getSelectedCategory() {
        return categoriesModel.getSelectedData();
    }

    @Override
    public boolean areSubCategoriesIncluded() {
        return checkBoxSub.isSelected();
    }

    @Override
    public boolean mustGenerateDuration() {
        return checkBoxDuration.isSelected();
    }

    @Override
    public boolean mustGenerateResolution() {
        return checkBoxResolution.isSelected();
    }

    @Override
    public boolean mustGenerateImage() {
        return checkBoxImage.isSelected();
    }
}