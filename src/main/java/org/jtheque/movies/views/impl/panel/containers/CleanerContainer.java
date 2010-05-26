package org.jtheque.movies.views.impl.panel.containers;

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

import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.ui.utils.components.JThequeCheckBox;

import java.awt.Color;

/**
 * A check box to select a name cleaner.
 *
 * @author Baptiste Wicht
 */
public final class CleanerContainer extends JThequeCheckBox {
    private final NameCleaner cleaner;

    /**
     * Construct a new CleanerContainer for the specified name cleaner.
     *
     * @param cleaner The name cleaner.
     */
    public CleanerContainer(NameCleaner cleaner) {
        super(cleaner.getTitleKey(), cleaner.getTitleReplaces());

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