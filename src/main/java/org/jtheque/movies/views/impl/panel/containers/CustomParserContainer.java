package org.jtheque.movies.views.impl.panel.containers;

import org.jtheque.movies.services.impl.parsers.FileParser;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.FlowLayout;

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