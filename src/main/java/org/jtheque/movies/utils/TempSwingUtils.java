package org.jtheque.movies.utils;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.Internationalizable;
import org.jtheque.core.managers.view.impl.components.JThequeCheckBox;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.ButtonBarBuilder;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.Color;

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

public final class TempSwingUtils {
	private TempSwingUtils(){
		super();
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
     * @param panel The panel builder to add the button bar to. 
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

	public static JCheckBox addFilthyCheckbox(PanelBuilder panel, String key, Object constraints){
		JCheckBox checkBox = new JThequeCheckBox(key);

        checkBox.setForeground(Color.white);
        checkBox.setOpaque(false);

		return panel.add(checkBox, constraints);
	}

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