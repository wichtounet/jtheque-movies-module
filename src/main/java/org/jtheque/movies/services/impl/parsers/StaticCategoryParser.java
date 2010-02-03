package org.jtheque.movies.services.impl.parsers;

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

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyComboBox;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;

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
    private final DataContainerCachedComboBoxModel<Category> categoriesModel;
    private final JComboBox categoriesComboBox;

    /**
     * Construct a new StaticCategoryParser.
     */
    public StaticCategoryParser() {
        super();

        categoriesModel = new DataContainerCachedComboBoxModel<Category>(
                CoreUtils.<DataContainer<Category>>getBean("categoriesService"));

        categoriesComboBox = new FilthyComboBox(categoriesModel);
    }

    @Override
    public String getTitle() {
        return "";
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