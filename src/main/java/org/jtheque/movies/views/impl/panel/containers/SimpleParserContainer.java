package org.jtheque.movies.views.impl.panel.containers;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.movies.services.impl.parsers.FileParser;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

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
public final class SimpleParserContainer extends JCheckBox implements ParserContainer {
    private final FileParser parser;

    /**
     * Construct a new ParserContainer for the specified file parser.
     *
     * @param parser The file parser.
     */
    public SimpleParserContainer(FileParser parser){
        super(parser.getTitle());

        this.parser = parser;

        setBackground(Managers.getManager(IViewManager.class).getViewDefaults().getBackgroundColor());
    }

	@Override
	public FileParser getParser(){
        return parser;
    }

	@Override
	public JComponent getImpl(){
		return this;
	}
}