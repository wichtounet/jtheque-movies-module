package org.jtheque.movies.services.impl.parsers;

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

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.primary.utils.views.DataContainerCachedComboBoxModel;
import org.jtheque.ui.utils.filthy.FilthyComboBox;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

/**
 * A category parser who return a category choose by the user.
 *
 * @author Baptiste Wicht
 */
public final class StaticCategoryParser implements FileParser {
    private static final Object[] REPLACES = new Object[0];

    private final DataContainerCachedComboBoxModel<Category> categoriesModel;
    private final JComboBox categoriesComboBox;

    /**
     * Construct a new StaticCategoryParser.
     */
    public StaticCategoryParser(ICategoriesService categoriesService) {
        super();

        categoriesModel = new DataContainerCachedComboBoxModel<Category>(categoriesService);

        categoriesComboBox = new FilthyComboBox(categoriesModel);
    }

    @Override
    public String getTitleKey() {
        return "";
    }

    @Override
    public Object[] getTitleReplaces() {
        return REPLACES;
    }

    @Override
    public void parseFilePath(File file) {

    }

    @Override
    public String clearFileName(String fileName) {
        return fileName;
    }

    @Override
    public Collection<Category> getExtractedCategories() {
        return Arrays.asList(categoriesModel.getSelectedData());
    }

    @Override
    public boolean hasCustomView() {
        return true;
    }

    @Override
    public JComponent getCustomView() {
        return categoriesComboBox;
    }
}