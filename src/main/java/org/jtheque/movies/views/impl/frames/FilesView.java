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

import org.jdesktop.swingx.JXTable;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.utils.TempSwingUtils;
import org.jtheque.movies.views.able.IFilesView;
import org.jtheque.movies.views.able.models.ICleanModel;
import org.jtheque.movies.views.impl.actions.files.RefreshFilesListAction;
import org.jtheque.movies.views.impl.models.FilesTableModel;
import org.jtheque.movies.views.impl.panel.FileRenderer;
import org.jtheque.movies.views.impl.panel.MovieChooserEditor;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JTable;
import java.io.File;

/**
 * A view implementation to view and edit the unused files of the application.
 *
 * @author Baptiste Wicht
 */
public final class FilesView extends SwingFilthyBuildedDialogView<ICleanModel> implements IFilesView {
    private FilesTableModel tableModel;

    /**
     * Construct a new FilesView.
     *
     */
    public FilesView() {
        super();

        build();
    }

    @Override
    protected void initView() {
        setTitleKey("files.title");
        setResizable(true);
    }

    @Override
    protected void buildView(PanelBuilder builder) {
        tableModel = new FilesTableModel();

        JXTable tableMovies = new JXTable(tableModel);
        tableMovies.setSortable(false);
        tableMovies.getTableHeader().setReorderingAllowed(false);
        tableMovies.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tableMovies.setColumnControlVisible(false);
        tableMovies.packAll();
        tableMovies.setDefaultEditor(File.class, new MovieChooserEditor());
        tableMovies.setDefaultRenderer(File.class, new FileRenderer());

        builder.addScrolled(tableMovies, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1.0, 1.0));

        TempSwingUtils.addFilthyButtonBar(builder, builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL),
                new RefreshFilesListAction(), getCloseAction("files.actions.close"));
    }

    @Override
    public void refreshData(){
        tableModel.refresh();
    }
}