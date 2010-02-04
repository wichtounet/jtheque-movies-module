package org.jtheque.movies.utils;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.Internationalizable;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.components.JThequeCheckBox;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.utils.ui.ButtonBarBuilder;
import org.jtheque.utils.ui.ImageUtils;
import org.jtheque.utils.ui.SizeTracker;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

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
 * A temporary swing utility class. This class must be moved to JTheque Core/Utils at
 * the next release of this two projects.
 *
 * @author Baptiste Wicht
 */
public final class SwingUtils {
    private static final BufferedImage LIGHT;
    private static final LinearGradientPaint BACKGROUND_PAINT;

    static {
        BACKGROUND_PAINT = CoreUtils.getBean("backgroundPaint");
        LIGHT = Managers.getManager(IResourceManager.class).getImage(IMoviesModule.IMAGES_BASE_NAME, "light", ImageType.PNG);
    }

    /**
     * Utility class, not instanciable.
     */
    private SwingUtils() {
        super();
    }

    /**
     * Paint a filthy background to a panel.
     *
     * @param g             The graphics to paint to.
     * @param gradientImage The gradient image to use.
     * @param tracker       The size tracker of the panel.
     * @param panel         The panel to paint.
     * @return The current gradient image buffer.
     */
    public static Image paintFilthyBackground(Graphics g, Image gradientImage, SizeTracker tracker, Component panel) {
        Image gradient = gradientImage;

        Graphics2D g2 = (Graphics2D) g;

        if (gradient == null || tracker.hasSizeChanged()) {
            gradient = ImageUtils.createCompatibleImage(panel.getWidth(), panel.getHeight());
            Graphics2D g2d = (Graphics2D) gradient.getGraphics();
            Composite composite = g2.getComposite();
            
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setPaint(BACKGROUND_PAINT);
            g2d.fillRect(0, 0, panel.getWidth(), panel.getHeight());
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

            g2d.drawImage(LIGHT, 0, 0, panel.getWidth(), LIGHT.getHeight(), null);
            g2d.setComposite(composite);
            g2d.dispose();
        }

        g2.drawImage(gradient, 0, 0, null);

        tracker.updateSize();

        return gradient;
    }

    /**
     * Create a filthy titled border.
     *
     * @param key The internationalization key.
     * @return The border.
     */
    public static Border createFilthyTitledBorder(String key) {
        TitledBorder border = BorderFactory.createTitledBorder(Managers.getManager(ILanguageManager.class).getMessage(key));

        border.setTitleColor(Color.white);

        Managers.getManager(ILanguageManager.class).addInternationalizable(new BorderUpdater(border, key));

        return border;
    }

    /**
     * Add a button bar.
     *
     * @param panel       The panel builder to add the button bar to.
     * @param constraints The constraints to use to add to the panel.
     * @param actions     The actions to add to the button bar.
     */
    public static void addFilthyButtonBar(PanelBuilder panel, Object constraints, Action... actions) {
        ButtonBarBuilder builder = new ButtonBarBuilder();

        builder.addGlue();

        builder.addActions(actions);

        ((JComponent) builder.getPanel()).setOpaque(false);

        panel.add(builder.getPanel(), constraints);
    }

    /**
     * Add a filthy checkbox to the panel builder.
     *
     * @param panel       The panel builder to add the check box to.
     * @param key         The i18n key of the check box.
     * @param constraints The constraints to use to add the check box to the builder.
     * @return The added checkbox.
     */
    public static JCheckBox addFilthyCheckbox(PanelBuilder panel, String key, Object constraints) {
        JCheckBox checkBox = new JThequeCheckBox(key);

        checkBox.setForeground(Color.white);
        checkBox.setOpaque(false);

        return panel.add(checkBox, constraints);
    }

    /**
     * A Border Updater. It seems a internationalizable who keeps the title of a border up to date
     * with the current locale.
     *
     * @author Baptiste Wicht
     */
    private static final class BorderUpdater implements Internationalizable {
        private final TitledBorder border;
        private final String key;

        /**
         * Construct a new BorderUpdater for a specific border with a internationalization key.
         *
         * @param border The titled border.
         * @param key    The internationalization key.
         */
        BorderUpdater(TitledBorder border, String key) {
            super();

            this.border = border;
            this.key = key;
        }

        @Override
        public void refreshText() {
            border.setTitle(Managers.getManager(ILanguageManager.class).getMessage(key));
		}
	}
}