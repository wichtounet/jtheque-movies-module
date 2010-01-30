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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.utils.ui.ImageUtils;
import org.jtheque.utils.ui.SizeTracker;

import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public final class FilthyBackgroundPanel extends JPanel {
	private final SizeTracker tracker = new SizeTracker(this);
	private Image gradientImage;

	private final BufferedImage light;
    private final LinearGradientPaint backgroundGradient;

	public FilthyBackgroundPanel(){
		super();

		backgroundGradient = CoreUtils.getBean("backgroundPaint");
		light = Managers.getManager(IResourceManager.class).getImage(IMoviesModule.IMAGES_BASE_NAME, "light", ImageType.PNG);
	}

	@Override
	public void paintComponent(Graphics g){
		if (!isVisible()){
			return;
		}

		Graphics2D g2 = (Graphics2D) g;

		if (gradientImage == null || tracker.hasSizeChanged()){
			gradientImage = ImageUtils.createCompatibleImage(getWidth(), getHeight());
			Graphics2D g2d = (Graphics2D) gradientImage.getGraphics();
			Composite composite = g2.getComposite();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setPaint(backgroundGradient);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

			g2d.drawImage(light, 0, 0, getWidth(), light.getHeight(), null);
			g2d.setComposite(composite);
			g2d.dispose();
		}

		g2.drawImage(gradientImage, 0, 0, null);

		tracker.updateSize();
	}
}
