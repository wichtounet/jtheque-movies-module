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
import org.jtheque.core.managers.view.impl.components.panel.FileChooserPanel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.movies.views.able.IImportFolderView;
import org.jtheque.movies.views.impl.panel.ParserContainer;
import org.jtheque.movies.views.impl.models.FilesListModel;
import org.jtheque.movies.services.impl.parsers.FileParser;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.KeyStroke;
import javax.swing.Action;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.ArrayList;
import java.io.File;

/**
 * View for the auto-import function.
 *
 * @author Baptiste Wicht
 */
public final class ImportFolderView extends SwingDialogView implements IImportFolderView {
    private static final long serialVersionUID = 4633039680922071605L;

    private FileChooserPanel directoryChooser;
    private JList listFiles;

    private FilesListModel modelListFiles;

    private Phase phase = Phase.CHOOSE_FOLDER;
    
    private final Action searchTitlesAction;
    private final Action deleteTitleAction;
    private final Action validateAction;
    private final Action cancelAction;

    private final Collection<ParserContainer> parserContainers;

    /**
     * Construct a new ImportFolderView. 
     * 
     * @param frame The parent frame. 
     * @param searchTitlesAction The action to search for files. 
     * @param deleteTitleAction The action to delete a file. 
     * @param validateAction The actions to validate the view. 
     * @param cancelAction The action to cancel the view. 
     * @param parsers A List of parsers used to extract the categories from the file name. 
     */
    public ImportFolderView(Frame frame, Action searchTitlesAction, Action deleteTitleAction,
                            Action validateAction, Action cancelAction, Collection<FileParser> parsers) {
        super(frame);
        
        this.searchTitlesAction = searchTitlesAction;
        this.deleteTitleAction = deleteTitleAction;
        this.validateAction = validateAction;
        this.cancelAction = cancelAction;
        
        parserContainers = new ArrayList<ParserContainer>(parsers.size());

        for(FileParser p : parsers){
            parserContainers.add(new ParserContainer(p));
        }
             
        build();
    }

    /**
     * Build the view. 
     * 
     */
    private void build() {
        setTitleKey("movie.auto.folder.title");
        setContentPane(buildContentPane());
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Init the graphics user interface.
     *
     * @return The content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new PanelBuilder();

        addDirectoryChooser(builder);
        addTitleList(builder);
        addParsers(builder);

        return builder.getPanel();
    }

    /**
     * Add the directory chooser to the view. 
     * 
     * @param builder The parent builder
     */
    private void addDirectoryChooser(PanelBuilder builder) {
        directoryChooser = new FileChooserPanel();
        directoryChooser.setDirectoriesOnly();
        directoryChooser.setTextKey("movie.auto.folder.directory");

        builder.add(directoryChooser, builder.gbcSet(0, 0, GridBagConstraints.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 1.0, 0.0));
        
        builder.addButton(searchTitlesAction, builder.gbcSet(1, 0, GridBagConstraints.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING));
    }

    /**
     * Add the list of files to the view. 
     * 
     * @param builder The parent builder
     */
    private void addTitleList(PanelBuilder builder) {
        modelListFiles = new FilesListModel();

        listFiles = new JList(modelListFiles);
        listFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listFiles.setVisibleRowCount(5);
        listFiles.getActionMap().put("delete", deleteTitleAction);
        listFiles.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");

        builder.addScrolled(listFiles, builder.gbcSet(0, 1, GridBagConstraints.BOTH, GridBagUtils.BELOW_BASELINE_LEADING, 0, 1, 1.0, 1.0));
    }

    /**
     * Add the parsers to the view. 
     * 
     * @param builder The parent builder
     */
    private void addParsers(PanelBuilder builder) {
        builder.addI18nLabel("movie.auto.categories", builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 0, 1, 1.0, 1.0));
        
        int i = 3;
        
        for(ParserContainer container : parserContainers){
            builder.add(container, builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 0, 1, 1.0, 1.0));
        }
        
        builder.addButtonBar(builder.gbcSet(0, ++i, GridBagConstraints.HORIZONTAL, GridBagUtils.BELOW_BASELINE_LEADING, 0, 1, 1.0, 1.0), 
                validateAction, cancelAction);
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
    public void clearFiles() {
        modelListFiles.clear();
    }

    @Override
    public void setFiles(Collection<File> files) {
        modelListFiles.setFiles(files);
    }

    @Override
    public Collection<File> getFiles() {
        return modelListFiles.getFiles();
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
    
    @Override
    public Collection<FileParser> getSelectedParsers(){
        Collection<FileParser> parsers = new ArrayList<FileParser>(5);
        
        for(ParserContainer container : parserContainers){
            if(container.isSelected()){
                parsers.add(container.getParser());
            }
        }
        
        return parsers;
    }
}