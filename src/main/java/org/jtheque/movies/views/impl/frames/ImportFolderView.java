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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.components.model.SimpleListModel;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.utils.TempSwingUtils;
import org.jtheque.movies.views.able.IImportFolderView;
import org.jtheque.movies.views.impl.actions.movies.folder.DeleteFileAction;
import org.jtheque.movies.views.impl.actions.movies.folder.ImportFilesAction;
import org.jtheque.movies.views.impl.actions.movies.folder.SearchFilesAction;
import org.jtheque.movies.views.impl.panel.FilthyFileChooserPanel;
import org.jtheque.movies.views.impl.panel.containers.ParserContainer;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Collection;

/**
 * View for the auto-import function.
 *
 * @author Baptiste Wicht
 */
public final class ImportFolderView extends AbstractParserView implements IImportFolderView {
    private FilthyFileChooserPanel directoryChooser;
    private JList listFiles;

    private SimpleListModel<File> modelListFiles;

    private Phase phase = Phase.CHOOSE_FOLDER;

    /**
     * Construct a new ImportFolderView.
     *
     * @param parsers A List of parsers used to extract the categories from the file name.
     */
    public ImportFolderView(Collection<FileParser> parsers) {
        super(parsers);
    }

    @Override
    protected void initView() {
        setTitleKey("movie.auto.folder.title");
    }

    @Override
    protected void buildView(PanelBuilder builder) {
        addDirectoryChooser(builder);
        addTitleList(builder);
        addParsers(builder);
    }

    /**
     * Add the directory chooser to the view.
     *
     * @param builder The parent builder
     */
    private void addDirectoryChooser(PanelBuilder builder) {
        directoryChooser = builder.add(new FilthyFileChooserPanel(), builder.gbcSet(0, 0, GridBagConstraints.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 1.0, 0.0));
        directoryChooser.setDirectoriesOnly();
        directoryChooser.setTextKey("movie.auto.folder.directory");

        builder.addButton(new SearchFilesAction(), builder.gbcSet(1, 0, GridBagConstraints.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING));
    }

    /**
     * Add the list of files to the view.
     *
     * @param builder The parent builder
     */
    private void addTitleList(PanelBuilder builder) {
        modelListFiles = new SimpleListModel<File>();

        listFiles = builder.addList(modelListFiles, null, builder.gbcSet(0, 1, GridBagConstraints.BOTH, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 1.0));
        listFiles.setVisibleRowCount(5);
        listFiles.getActionMap().put("delete", new DeleteFileAction());
        listFiles.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
    }

    /**
     * Add the parsers to the view.
     *
     * @param builder The parent builder
     */
    private void addParsers(PanelBuilder builder) {
        builder.addI18nLabel("movie.auto.categories", builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 0, 1, 1.0, 0.0));

        int i = 3;

        for (ParserContainer container : getContainers()) {
            builder.add(container.getImpl(), builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 0, 1, 1.0, 0.0));
        }

        TempSwingUtils.addFilthyButtonBar(builder, builder.gbcSet(0, ++i, GridBagConstraints.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 0, 1, 1.0, 0.0),
                new ImportFilesAction(), getCloseAction("movie.auto.folder.actions.cancel"));
    }

    @Override
    public String getFolderPath() {
        return directoryChooser.getFilePath();
    }

    @Override
    public void removeSelectedFile() {
        modelListFiles.remove(listFiles.getSelectedIndex());
    }

    @Override
    public void setFiles(Collection<File> files) {
        modelListFiles.setElements(files);
    }

    @Override
    public Collection<File> getFiles() {
        return modelListFiles.getObjects();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        if (Phase.CHOOSE_FOLDER == phase) {
            ValidationUtils.rejectIfEmpty(directoryChooser.getFilePath(), "movie.auto.folder.errors.folderEmpty", errors);
        } else if (Phase.CHOOSE_FILES == phase) {
            ValidationUtils.rejectIfEmpty(listFiles, "movie.auto.folder.errors.filesEmpty", errors);
        }
    }

    @Override
    public boolean validateContent(Phase phase) {
        this.phase = phase;

        return validateContent();
    }
}