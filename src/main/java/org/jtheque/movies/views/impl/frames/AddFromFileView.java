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

import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.utils.TempSwingUtils;
import org.jtheque.movies.views.able.IAddFromFileView;
import org.jtheque.movies.views.impl.actions.movies.auto.ValidateAddFromFileViewAction;
import org.jtheque.movies.views.impl.panel.FilthyFileChooserPanel;
import org.jtheque.movies.views.impl.panel.containers.ParserContainer;
import org.jtheque.utils.ui.GridBagUtils;

import java.io.File;
import java.util.Collection;

/**
 * User interface to add a movie from a file.
 *
 * @author Baptiste Wicht
 */
public final class AddFromFileView extends AbstractParserView implements IAddFromFileView {
    private FilthyFileChooserPanel fileChooser;

    /**
     * Construct a new Category View.
     *
     * @param parsers The category parsers.
     */
    public AddFromFileView(Collection<FileParser> parsers) {
        super(parsers);
    }

    @Override
    protected void initView() {
        setTitleKey("movie.auto.title");
        setResizable(false);
    }

    @Override
    protected void buildView(PanelBuilder builder) {
        fileChooser = builder.add(new FilthyFileChooserPanel(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));
        fileChooser.setFilesOnly();
        fileChooser.setTextKey("movie.auto.file");

        builder.addI18nLabel("movie.auto.categories", builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL));

        int i = 1;

        for (ParserContainer container : getContainers()) {
            builder.add(container.getImpl(), builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL));
        }

        TempSwingUtils.addFilthyButtonBar(builder, builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL),
                new ValidateAddFromFileViewAction(), getCloseAction("movie.auto.actions.cancel"));
    }

    @Override
    public void display() {
        IMovieController controller = getBean("movieController");

        if (controller.isEditing()) {
            getManager().displayI18nText("movie.dialogs.currentEdit");
        } else {
            super.display();
        }
    }

    @Override
    public String getFilePath() {
        return fileChooser.getFilePath();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        ConstraintManager.validate(Movie.FILE, getFilePath(), errors);

        if (errors.isEmpty() && !new File(getFilePath()).exists()) {
            errors.add(new InternationalizedError("movie.errors.filenotfound"));
        }
    }
}