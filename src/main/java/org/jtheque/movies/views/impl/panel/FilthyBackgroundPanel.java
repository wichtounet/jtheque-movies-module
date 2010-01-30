package org.jtheque.movies.views.impl.panel;

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

import org.jtheque.movies.utils.TempSwingUtils;
import org.jtheque.utils.ui.SizeTracker;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

public final class FilthyBackgroundPanel extends JPanel {
	private final SizeTracker tracker = new SizeTracker(this);
	private Image gradientImage;

	@Override
	public void paintComponent(Graphics g){
		if (!isVisible()){
			return;
		}

		gradientImage = TempSwingUtils.paintFilthyBackground(g, gradientImage, tracker, this);
	}
}