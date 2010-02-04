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

import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.movies.utils.SwingUtils;
import org.jtheque.movies.views.able.ICleanView;
import org.jtheque.movies.views.able.models.ICleanModel;
import org.jtheque.movies.views.impl.actions.clean.ValidateCleanViewAction;
import org.jtheque.movies.views.impl.models.CleanModel;
import org.jtheque.movies.views.impl.panel.containers.CleanerContainer;
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

        build();
    }

    @Override
    protected void initView() {
        setModel(new CleanModel());
        setTitleKey("movie.clean.title");
        setResizable(false);
    }

    @Override
    protected void buildView(PanelBuilder builder) {
        PanelBuilder optionsBuilder = builder.addPanel(builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));
        optionsBuilder.getPanel().setBorder(SwingUtils.createFilthyTitledBorder("movie.clean.options"));

        int i = 0;

        for (CleanerContainer container : cleanerContainers) {
            optionsBuilder.add(container, builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL));
        }

        checkBoxSub = SwingUtils.addFilthyCheckbox(builder, "movie.clean.subcategories",
                builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL));

        SwingUtils.addFilthyButtonBar(builder, builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL),
                new ValidateCleanViewAction(), getCloseAction("movie.auto.actions.cancel"));
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