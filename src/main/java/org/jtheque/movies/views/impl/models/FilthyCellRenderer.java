package org.jtheque.movies.views.impl.models;

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

import org.jtheque.primary.view.impl.models.tree.TreeElement;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Color;
import java.awt.Component;

/**
 * A cell renderer to display an icon in the JTree.
 *
 * @author Baptiste Wicht
 */
public final class FilthyCellRenderer extends DefaultTreeCellRenderer {
    /**
     * Construct a new FilthyCellRenderer.
     */
    public FilthyCellRenderer() {
        super();

        setOpaque(false);

        setBackground(new Color(0, 0, 0, 0));
        setBackgroundNonSelectionColor(new Color(0, 0, 0, 0));

        setTextSelectionColor(Color.white);
        setTextNonSelectionColor(Color.white);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        TreeElement element = (TreeElement) value;

        setText(element.getElementName());

        if (element.getIcon() != null) {
            setIcon(element.getIcon());
        }

        return this;
    }
}