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

import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IFilesView;
import org.jtheque.movies.views.able.models.ICleanModel;
import org.jtheque.movies.views.impl.models.FilesTableModel;
import org.jtheque.movies.views.impl.panel.FileRenderer;
import org.jtheque.movies.views.impl.panel.MovieChooserEditor;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;

import org.jdesktop.swingx.JXTable;

import javax.annotation.Resource;
import javax.swing.JTable;

import java.io.File;

/**
 * A view implementation to view and edit the unused files of the application.
 *
 * @author Baptiste Wicht
 */
public final class FilesView extends SwingFilthyBuildedDialogView<ICleanModel> implements IFilesView {
    private FilesTableModel tableModel;

    @Resource
    private IMoviesService moviesService;

    @Override
    protected void initView() {
        setTitleKey("files.title");
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        tableModel = new FilesTableModel(moviesService);

        JXTable tableMovies = new JXTable(tableModel);
        tableMovies.setSortable(false);
        tableMovies.getTableHeader().setReorderingAllowed(false);
        tableMovies.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tableMovies.setColumnControlVisible(false);
        tableMovies.packAll();
        tableMovies.setDefaultEditor(File.class, new MovieChooserEditor());
        tableMovies.setDefaultRenderer(File.class, new FileRenderer());

        builder.addScrolled(tableMovies, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1.0, 1.0));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL),
                getAction("files.actions.refresh"),
                getAction("files.actions.close"));
    }

    @Override
    public void refreshData() {
        tableModel.refresh();
    }
}