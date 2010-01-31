package org.jtheque.movies.views.impl.panel.players;

import chrriis.dj.nativeswing.swtimpl.components.win32.JWMediaPlayer;

import java.awt.*;
import java.io.File;

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
 * A WMP panel implementation.
 *
 * @author Baptiste Wicht
 */
public final class JPanelWMP extends ViewerPanel {
    private JWMediaPlayer player;

    @Override
    protected void addPlayer() {
        player = new JWMediaPlayer();
        player.setControlBarVisible(true);
        add(player, BorderLayout.CENTER);
    }

    @Override
    public void setFile(File f) {
        super.setFile(f);
        player.load(f.getAbsolutePath());
    }

    @Override
    public void stop() {
        player.getWMPControls().stop();
    }
}