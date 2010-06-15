package org.jtheque.movies.views.impl.models;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.images.able.IImageService;
import org.jtheque.primary.utils.views.tree.TreeElement;

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
    private final IImageService imageService;

    /**
     * Construct a new FilthyCellRenderer.
     *
     * @param imageService The resource service.
     */
    public FilthyCellRenderer(IImageService imageService) {
        super();

        this.imageService = imageService;

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

        if (element.getIcon(imageService) != null) {
            setIcon(element.getIcon(imageService));
        }

        return this;
    }
}