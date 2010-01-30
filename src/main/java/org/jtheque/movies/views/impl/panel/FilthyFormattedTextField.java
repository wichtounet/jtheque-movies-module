package org.jtheque.movies.views.impl.panel;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.ViewDefaults;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.utils.ui.PaintUtils;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

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

public final class FilthyFormattedTextField extends JPanel {
    private final JFormattedTextField textField;

    /**
     * Construct a new FilthyFormattedTextField.
	 * 
	 * @param formatter The formatter for the display of the text field.
	 */
    public FilthyFormattedTextField(MaskFormatter formatter) {
        super();

		initUI();

		textField = new JFormattedTextField(formatter);

        initComponent();
    }

	public FilthyFormattedTextField(NumberFormatter formatter){
		super();

		initUI();

		textField = new JFormattedTextField(formatter);

        initComponent();
	}

	private void initUI(){
		IResourceManager resources = Managers.getManager(IResourceManager.class);

		Color inputColor = resources.getColor("filthyInputColor");
		Color inputBorderColor = resources.getColor("filthyInputBorderColor");

		setLayout(new BorderLayout());
		setBackground(inputColor);
		setBorder(new CompoundBorder(
				BorderFactory.createLineBorder(inputBorderColor, 2),
				BorderFactory.createEmptyBorder(2, 2, 2, 2)));
	}

	private void initComponent() {
        makeFilthy(textField);

        add(textField);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        textField.setEnabled(enabled);
    }

    /**
     * Return the entered text.
     *
     * @return The entered text.
     */
    public String getText() {
        return textField.getText();
    }

    /**
     * Set the text.
     *
     * @param t The text.
     */
    public void setText(String t) {
        textField.setText(t);
    }

    /**
     * Return the text field.
     *
     * @return The text field.
     */
    public JTextField getTextField() {
        return textField;
    }

    @Override
    public void paint(Graphics g) {
        PaintUtils.initHints((Graphics2D) g);

        super.paint(g);
    }

    /**
     * Make a component filthy.
     *
     * @param field The field to make filthy.
     */
    private static void makeFilthy(JTextComponent field) {
        ViewDefaults defaults = Managers.getManager(IViewManager.class).getViewDefaults();

        Font inputFont = defaults.getFilthyInputFont();

        Color inputTextColor = defaults.getFilthyForegroundColor();
        Color inputSelectionColor = defaults.getFilthyForegroundColor();
        Color inputSelectedTextColor = defaults.getFilthyBackgroundColor();

        field.setOpaque(false);
        field.setBorder(Borders.EMPTY_BORDER);
        field.setForeground(inputTextColor);
        field.setSelectedTextColor(inputSelectedTextColor);
        field.setSelectionColor(inputSelectionColor);
        field.setCaretColor(inputSelectionColor);
        field.setFont(inputFont);
    }
}
