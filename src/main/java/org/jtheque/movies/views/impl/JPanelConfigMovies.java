package org.jtheque.movies.views.impl;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.components.panel.FileChooserPanel;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.utils.OSUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.util.Collection;

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

/**
 * A config panel to configure the opening system.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigMovies extends JPanel implements IOpeningConfigView {
    private JComboBox combo;

    private FileChooserPanel fileChooser;

    @Override
    public String getTitle() {
        return CoreUtils.getMessage("movie.config");
    }

    @Override
    public void build() {
        SwingUtils.inEdt(new BuildViewRunnable());
    }

    /**
     * Fill all the fields with the current informations.
     */
    private void fillAllFields() {
        IMoviesModule module = CoreUtils.getBean("moviesModule");

        combo.setSelectedItem(module.getConfig().getOpeningSystem());
        fileChooser.setFilePath(module.getConfig().getFFmpegLocation());
    }

    @Override
    public void apply() {
        IMoviesModule module = CoreUtils.getBean("moviesModule");

        module.getConfig().setOpeningSystem((IMovieConfiguration.Opening) combo.getSelectedItem());
        module.getConfig().setFFmpegLocation(fileChooser.getFilePath());
    }

    @Override
    public void cancel() {
        fillAllFields();
    }

    @Override
    public void validate(Collection<JThequeError> errors) {
        //Nothing to validate in the view
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    /**
     * A Runnable to build the view.
     *
     * @author Baptiste Wicht
     */
    private final class BuildViewRunnable implements Runnable {
        @Override
        public void run() {
            PanelBuilder parent = new JThequePanelBuilder(JPanelConfigMovies.this);

            addOpeningField(parent);
            addFFMPEGField(parent);

            fillAllFields();
        }

        /**
         * Add the field the configure the opening system to use.
         *
         * @param parent The parent to add.
         */
        private void addOpeningField(PanelBuilder parent) {
            PanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL));
            builder.getPanel().setBorder(Borders.createTitledBorder("movie.config.opening"));

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
        private void addFFMPEGField(PanelBuilder parent) {
            PanelBuilder builder = parent.addPanel(parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL));
            builder.getPanel().setBorder(Borders.createTitledBorder("movie.config.ffmpeg"));

            fileChooser = builder.add(new FileChooserPanel(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));
            fileChooser.setFilesOnly();
            fileChooser.setFileFilter(new SimpleFilter("Exe files", "exe"));
            fileChooser.setTextKey("movie.config.ffmpeg.file");
		}
	}
}