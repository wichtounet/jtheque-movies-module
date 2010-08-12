package org.jtheque.movies.views.impl;

import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.ui.components.Components;
import org.jtheque.ui.components.FileChooser;
import org.jtheque.ui.constraints.Constraint;
import org.jtheque.ui.utils.builded.OSGIBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.utils.OSUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.Resource;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.util.Collections;
import java.util.Map;

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

/**
 * A config panel to configure the opening system.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigMovies extends OSGIBuildedPanel implements IOpeningConfigView {
    private JComboBox combo;
    private FileChooser fileChooser;

    @Resource
    private IMoviesModule moviesModule;

    @Override
    public String getTitleKey() {
        return "movie.config";
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        addOpeningField(builder);
        addFFMPEGField(builder);

        fillAllFields();
    }

    /**
     * Add the field the configure the opening system to use.
     *
     * @param parent The parent to add.
     */
    private void addOpeningField(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL));
        builder.setI18nTitleBorder("movie.config.opening");

        combo = builder.add(new JComboBox(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));

        combo.addItem(IMovieConfiguration.Opening.SYSTEM);

        if (OSUtils.isLinux()) {
            combo.addItem(IMovieConfiguration.Opening.VLC);
        } else if (OSUtils.isWindows()) {
            combo.addItem(IMovieConfiguration.Opening.WMP);
        }
    }

    /**
     * Add the ffmpeg field to configure the location of ffmpeg.
     *
     * @param parent The panel builder.
     */
    private void addFFMPEGField(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL));
        builder.setI18nTitleBorder("movie.config.ffmpeg");

        fileChooser = builder.add(Components.newFileChooser(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));
        fileChooser.setFilesOnly();
        fileChooser.setFileFilter(new SimpleFilter("Exe files", "exe"));
        fileChooser.setTextKey("movie.config.ffmpeg.file");
    }

    /**
     * Fill all the fields with the current informations.
     */
    private void fillAllFields() {
        combo.setSelectedItem(moviesModule.getConfig().getOpeningSystem());
        fileChooser.setFilePath(moviesModule.getConfig().getFFmpegLocation());
    }

    @Override
    public void apply() {
        moviesModule.getConfig().setOpeningSystem((IMovieConfiguration.Opening) combo.getSelectedItem());
        moviesModule.getConfig().setFFmpegLocation(fileChooser.getFilePath());
    }

    @Override
    public void cancel() {
        fillAllFields();
    }

    @Override
    public Map<Object, Constraint> getConstraints() {
        return Collections.emptyMap();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}