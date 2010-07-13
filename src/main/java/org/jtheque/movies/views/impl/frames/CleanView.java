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

import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.movies.views.able.ICleanView;
import org.jtheque.movies.views.able.models.ICleanModel;
import org.jtheque.movies.views.impl.models.CleanModel;
import org.jtheque.movies.views.impl.panel.containers.CleanerContainer;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JCheckBox;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A view implementation to select the options to clean the name of movies.
 *
 * @author Baptiste Wicht
 */
public final class CleanView extends SwingFilthyBuildedDialogView<ICleanModel> implements ICleanView {
    private final Collection<CleanerContainer> cleanerContainers;

    private JCheckBox checkBoxSub;

    /**
     * Construct a new CleanView.
     *
     * @param cleaners The name cleaners.
     */
    public CleanView(Collection<NameCleaner> cleaners) {
        super();

        cleanerContainers = new ArrayList<CleanerContainer>(cleaners.size());

        for (NameCleaner p : cleaners) {
            cleanerContainers.add(new CleanerContainer(p));
        }
    }

    @Override
    protected void initView() {
        setModel(new CleanModel());
        setTitleKey("movie.clean.title");
        setResizable(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        I18nPanelBuilder optionsBuilder = builder.addPanel(builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));
        optionsBuilder.setI18nTitleBorder("movie.clean.options");

        int i = 0;

        for (CleanerContainer container : cleanerContainers) {
            optionsBuilder.add(container, builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL));
        }

        checkBoxSub = builder.addI18nCheckBox("movie.clean.subcategories", builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL),
                getAction("movie.actions.clean.validate"),
                getAction("movie.auto.actions.cancel"));
    }

    @Override
    public boolean areSubCategoriesIncluded() {
        return checkBoxSub.isSelected();
    }

    @Override
    public Collection<NameCleaner> getSelectedCleaners() {
        Collection<NameCleaner> cleaners = new ArrayList<NameCleaner>(5);

        for (CleanerContainer container : cleanerContainers) {
            if (container.isSelected()) {
                cleaners.add(container.getCleaner());
            }
        }

        return cleaners;
    }
}