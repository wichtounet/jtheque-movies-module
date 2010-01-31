package org.jtheque.movies.views.impl.panel.containers;

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

import org.jtheque.movies.services.impl.cleaners.NameCleaner;

import javax.swing.*;
import java.awt.*;

/**
 * A check box to select a name cleaner.
 *
 * @author Baptiste Wicht
 */
public final class CleanerContainer extends JCheckBox {
    private final NameCleaner cleaner;

    /**
     * Construct a new CleanerContainer for the specified name cleaner.
     *
     * @param cleaner The name cleaner.
     */
    public CleanerContainer(NameCleaner cleaner) {
        super(cleaner.getTitle());

        this.cleaner = cleaner;

        setForeground(Color.white);
        setOpaque(false);
    }

    /**
     * Return the name cleaner for this checkbox.
     *
     * @return The name cleaner.
     */
    public NameCleaner getCleaner() {
        return cleaner;
    }
}