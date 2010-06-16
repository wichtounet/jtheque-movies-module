package org.jtheque.movies.views.impl.panel.players;

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

import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.views.impl.actions.view.QuitPlayerViewAction;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

/**
 * A abstract viewer panel. This class construct the base of a frame and let the extended class to build the rest of the
 * view.
 *
 * @author Baptiste Wicht
 */
public final class ViewerPanel extends JPanel {
    private final JLabel labelFile;

    private final IMoviePlayer player;

    /**
     * Construct a new ViewerPanel.
     *
     * @param player          The movie player.
     * @param movieController The movie controller.
     */
    public ViewerPanel(IMoviePlayer player, IMovieController movieController) {
        super();

        this.player = player;

        setLayout(new BorderLayout());

        GridBagConstraints cons = new GridBagConstraints();
        Container playerFilePanel = new JPanel(new GridBagLayout());

        JComponent playerFileLabel = new JLabel("File: ");
        playerFileLabel.setOpaque(false);
        cons.gridx = 0;
        cons.gridy = 0;
        cons.insets = new Insets(2, 2, 2, 0);
        cons.fill = GridBagConstraints.HORIZONTAL;
        playerFilePanel.add(playerFileLabel, cons);

        labelFile = new JLabel();
        cons.gridx++;
        cons.weightx = 1;

        playerFilePanel.add(labelFile, cons);

        cons.gridx++;

        playerFilePanel.add(new JButton(new QuitPlayerViewAction(movieController)), cons);

        add(playerFilePanel, BorderLayout.NORTH);

        add(player.getComponent(), BorderLayout.CENTER);
    }

    /**
     * Set the file to read in the viewer.
     *
     * @param file The file to open.
     */
    public void setFile(File file) {
        labelFile.setText(file.getAbsolutePath());
        player.load(file);
    }

    /**
     * Stop the movie.
     */
    public void stop() {
        player.stop();
    }
}