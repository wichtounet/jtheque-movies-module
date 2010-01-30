package org.jtheque.movies.views.impl.frames;

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

import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.movies.services.impl.parsers.FileParser;
import org.jtheque.movies.views.impl.panel.containers.CustomParserContainer;
import org.jtheque.movies.views.impl.panel.containers.ParserContainer;
import org.jtheque.movies.views.impl.panel.containers.SimpleParserContainer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User interface to add a movie from a file.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractParserView extends SwingFilthyBuildedDialogView<IModel>{
    private final Collection<ParserContainer> parserContainers;

    /**
     * Construct a new Category View.
     *
     * @param parsers The category parsers.
     */
    public AbstractParserView(Collection<FileParser> parsers){
        super();

        parserContainers = new ArrayList<ParserContainer>(parsers.size());

        for (FileParser p : parsers){
			if(p.hasCustomView()){
				parserContainers.add(new CustomParserContainer(p));
			} else {
            	parserContainers.add(new SimpleParserContainer(p));
			}
        }

        build();
    }

	public Collection<ParserContainer> getContainers(){
		return parserContainers;
	}

    public Collection<FileParser> getSelectedParsers(){
        Collection<FileParser> parsers = new ArrayList<FileParser>(5);

        for (ParserContainer container : parserContainers){
            if (container.isSelected()){
                parsers.add(container.getParser());
            }
        }

        return parsers;
    }
}