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

import org.jtheque.errors.able.IError;
import org.jtheque.movies.services.able.IFilesService;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.views.able.IImportFolderView;
import org.jtheque.movies.views.impl.actions.movies.folder.DeleteFileAction;
import org.jtheque.movies.views.impl.actions.movies.folder.ImportFilesAction;
import org.jtheque.movies.views.impl.actions.movies.folder.SearchFilesAction;
import org.jtheque.movies.views.impl.panel.containers.ParserContainer;
import org.jtheque.ui.utils.ValidationUtils;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.filthy.FilthyFileChooserPanel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.ui.utils.models.SimpleListModel;

import javax.swing.JList;
import javax.swing.KeyStroke;
import java.awt.GridBagConstraints;
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
    protected void buildView(I18nPanelBuilder builder) {
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

        builder.addButton(new SearchFilesAction(getService(IFilesService.class), this),
                builder.gbcSet(1, 0, GridBagConstraints.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING));
    }

    /**
     * Add the list of files to the view.
     *
     * @param builder The parent builder
     */
    private void addTitleList(PanelBuilder builder) {
        modelListFiles = new SimpleListModel<File>();

        listFiles = builder.addScrolledList(modelListFiles, null, builder.gbcSet(0, 1, GridBagConstraints.BOTH, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 1.0));
        listFiles.setVisibleRowCount(5);
        listFiles.getActionMap().put("delete", new DeleteFileAction(this));
        listFiles.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
    }

    /**
     * Add the parsers to the view.
     *
     * @param builder The parent builder
     */
    private void addParsers(I18nPanelBuilder builder) {
        builder.addI18nLabel("movie.auto.categories", builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 0, 1, 1.0, 0.0));

        int i = 3;

        for (ParserContainer container : getContainers()) {
            builder.add(container.getImpl(), builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 0, 1, 1.0, 0.0));
        }

        builder.addButtonBar(builder.gbcSet(0, ++i, GridBagConstraints.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 0, 1, 1.0, 0.0),
                new ImportFilesAction(this, getService(IFilesService.class)), 
                getCloseAction("movie.auto.folder.actions.cancel"));
    }

    @Override
    public String getFolderPath() {
        return directoryChooser.getFilePath();
    }

    @Override
    public void removeSelectedFile() {
        modelListFiles.removeElement((File) listFiles.getSelectedValue());
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
    protected void validate(Collection<IError> errors) {
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