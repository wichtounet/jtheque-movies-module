package org.jtheque.movies.services.impl.parsers;

import org.jtheque.movies.persistence.od.able.Category;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

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

abstract class AbstractSimpleCategoryParser implements FileParser {
    private final Collection<Category> categories = new ArrayList<Category>(5);

    void addCategory(Category category){
        categories.add(category);
    }

    @Override
    public Collection<Category> getExtractedCategories() {
        return categories;
    }

    @Override
    public boolean hasCustomView() {
        return false;
    }

    @Override
    public JComponent getCustomView() {
        return null;
    }
}