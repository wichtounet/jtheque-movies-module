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

import javax.swing.JPanel;
import java.io.File;

/**
 * A viewer panel. It's a panel for a specific mode for movies. 
 * 
 * @author Baptiste Wicht
 */
public abstract class ViewerPanel extends JPanel {
    /**
     * Set the file to read in the viewer. 
     * 
     * @param file The file to open. 
     */
    public abstract void setFile(File file);

    /**
     * Stop the reader. 
     * 
     */
    public abstract void stop();
}