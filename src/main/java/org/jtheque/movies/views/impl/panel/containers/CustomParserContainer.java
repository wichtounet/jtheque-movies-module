package org.jtheque.movies.views.impl.panel.containers;

import org.jtheque.movies.services.impl.parsers.FileParser;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.FlowLayout;

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
 * A check box to select a file parser.
 *
 * @author Baptiste Wicht
 */
public final class CustomParserContainer extends JPanel implements ParserContainer {
    private final FileParser parser;

    private final JCheckBox checkBox;

    /**
     * Construct a new ParserContainer for the specified file parser.
     *
     * @param parser The file parser.
     */
    public CustomParserContainer(FileParser parser) {
        super();

        this.parser = parser;

        setOpaque(false);

        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        checkBox = new JCheckBox();
        checkBox.setOpaque(false);
        add(checkBox);

        add(parser.getCustomView());
    }

    @Override
    public FileParser getParser() {
        return parser;
    }

    @Override
    public boolean isSelected() {
        return checkBox.isSelected();
    }

    @Override
    public JComponent getImpl() {
        return this;
    }
}