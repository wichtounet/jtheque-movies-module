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

import org.jtheque.movies.controllers.able.IAddFromFileController;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.views.able.IAddFromFileView;
import org.jtheque.movies.views.impl.actions.movies.auto.ValidateAddFromFileViewAction;
import org.jtheque.movies.views.impl.panel.containers.ParserContainer;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.constraints.ValidFileConstraint;
import org.jtheque.ui.utils.filthy.FilthyFileChooserPanel;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.Resource;

import java.util.Collection;

/**
 * User interface to add a movie from a file.
 *
 * @author Baptiste Wicht
 */
public final class AddFromFileView extends AbstractParserView implements IAddFromFileView {
    private FilthyFileChooserPanel fileChooser;

    @Resource
    private IAddFromFileController addFromFileController;

    @Resource
    private IMovieController movieController;

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
    protected void buildView(I18nPanelBuilder builder) {
        fileChooser = builder.add(new FilthyFileChooserPanel(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));
        fileChooser.setFilesOnly();
        fileChooser.setTextKey("movie.auto.file");

        addConstraint(fileChooser, new ValidFileConstraint(Movie.FILE, 200));

        builder.addI18nLabel("movie.auto.categories", builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL));

        int i = 1;

        for (ParserContainer container : getContainers()) {
            builder.add(container.getImpl(), builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL));
        }

        builder.addButtonBar(builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL), new ValidateAddFromFileViewAction(addFromFileController), getCloseAction("movie.auto.actions.cancel"));
    }

    @Override
    public void display() {
        if (movieController.isEditing()) {
            getService(IUIUtils.class).displayI18nText("movie.dialogs.currentEdit");
        } else {
            super.display();
        }
    }

    @Override
    public String getFilePath() {
        return fileChooser.getFilePath();
    }
}