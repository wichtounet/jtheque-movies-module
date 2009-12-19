package org.jtheque.movies.views.impl.sort;

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

import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import javax.annotation.Resource;

/**
 * Only add the elements in the order given by the display list.
 *
 * @author Baptiste Wicht
 */
public final class NoneSorter implements Sorter {
    @Resource
    private IMovieView movieView;

    @Override
    public boolean canSort(String content, String sortType){
        return "None".equals(sortType);
    }

    @Override
    public void sort(JThequeTreeModel model){
        TreeElement root = model.getRoot();

        root.addAll(movieView.getDisplayList());

        model.setRootElement(root);
    }
}