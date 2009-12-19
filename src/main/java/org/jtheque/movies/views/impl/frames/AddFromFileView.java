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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.utils.CloseViewAction;
import org.jtheque.core.managers.view.impl.components.panel.FileChooserPanel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.views.able.IAddFromFileView;
import org.jtheque.movies.views.impl.actions.movies.auto.ValidateAddFromFileViewAction;
import org.jtheque.movies.views.impl.panel.ParserContainer;
import org.jtheque.utils.ui.GridBagUtils;

import java.awt.Container;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Collection;

/**
 * User interface to add a movie from a file.
 *
 * @author Baptiste Wicht
 */
public final class AddFromFileView extends SwingDialogView implements IAddFromFileView {
    private FileChooserPanel fileChooser;

    private final Collection<ParserContainer> parserContainers;

    /**
     * Construct a new Category View.
     *
     * @param parent         The parent frame.
     * @param parsers        The category parsers.
     */
    public AddFromFileView(Frame parent, Collection<FileParser> parsers){
        super(parent);

        parserContainers = new ArrayList<ParserContainer>(parsers.size());

        for (FileParser p : parsers){
            parserContainers.add(new ParserContainer(p));
        }

        build();
    }

    /**
     * Build the view.
     */
    private void build(){
        setTitleKey("movie.auto.title");
        setContentPane(buildContentPane());
        setResizable(false);
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build and return the content pane.
     *
     * @return The content pane.
     */
    private Container buildContentPane(){
        PanelBuilder builder = new PanelBuilder();

        fileChooser = builder.add(new FileChooserPanel(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));
        fileChooser.setFilesOnly();
        fileChooser.setTextKey("movie.auto.file");

        builder.addI18nLabel("movie.auto.categories", builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL));

        int i = 1;

        for (ParserContainer container : parserContainers){
            builder.add(container, builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL));
        }

        CloseViewAction closeAction = new CloseViewAction("movie.auto.actions.cancel");
        closeAction.setView(this);

        builder.addButtonBar(builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL), new ValidateAddFromFileViewAction(), closeAction);

        return builder.getPanel();
    }

    @Override
    public void display(){
        IMovieController controller = Managers.getManager(IBeansManager.class).getBean("movieController");

        if (controller.isEditing()){
            Managers.getManager(IViewManager.class).displayI18nText("movie.dialogs.currentEdit");
        } else {
            super.display();
        }
    }

    @Override
    protected void validate(Collection<JThequeError> errors){
    }

    @Override
    public String getFilePath(){
        return fileChooser.getFilePath();
    }

    @Override
    public Collection<FileParser> getSelectedParsers(){
        Collection<FileParser> parsers = new ArrayList<FileParser>(5);

        for (ParserContainer container : parserContainers){
            if (container.isSelected()){
                parsers.add(container.getParser());
            }
        }

        return parsers;
    }
}