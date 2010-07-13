package org.jtheque.movies.views.impl.frames;

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

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.views.able.IGenerateInfosView;
import org.jtheque.primary.utils.views.DataContainerCachedComboBoxModel;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.able.components.filthy.Filthy;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.Resource;
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

    @Resource
    private ICategoriesService categoriesService;

    @Override
    protected void initView() {
        setTitleKey("movie.generate.title");
        setResizable(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.addI18nLabel("data.titles.category", builder.gbcSet(0, 0));

        categoriesModel = new DataContainerCachedComboBoxModel<Category>(categoriesService);

        builder.addComboBox(categoriesModel, Filthy.newListRenderer(), builder.gbcSet(1, 0));

        checkBoxDuration = builder.addI18nCheckBox("movie.infos.duration", builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, 2, 1));
        checkBoxResolution = builder.addI18nCheckBox("movie.infos.resolution", builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, 2, 1));
        checkBoxImage = builder.addI18nCheckBox("movie.infos.image", builder.gbcSet(0, 3, GridBagUtils.HORIZONTAL, 2, 1));
        checkBoxSub = builder.addI18nCheckBox("movie.clean.subcategories", builder.gbcSet(0, 4, GridBagUtils.HORIZONTAL, 2, 1));

        builder.addButtonBar(builder.gbcSet(0, 5, GridBagUtils.HORIZONTAL, 2, 1),
                getAction("movie.actions.generate.validate"),
                getAction("movie.auto.actions.cancel"));
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