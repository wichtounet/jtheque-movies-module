package org.jtheque.movies.views.impl.panel.players;

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

import chrriis.dj.nativeswing.swtimpl.components.win32.JWMediaPlayer;

import javax.swing.JComponent;
import java.io.File;

/**
 * A WMP player implementation.
 *
 * @author Baptiste Wicht
 */
public final class WMPPlayer implements IMoviePlayer {
    private final JWMediaPlayer player;

    /**
     * Construct a new WMPPlayer. 
     */
    public WMPPlayer() {
        super();

        player = new JWMediaPlayer();
        player.setControlBarVisible(true);
    }

    @Override
    public void stop() {
        player.getWMPControls().stop();
    }

    @Override
    public void load(File f) {
        player.load(f.getAbsolutePath());
    }

    @Override
    public JComponent getComponent() {
        return player;
    }
}
