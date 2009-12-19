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

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Baptiste Wicht
 */
public final class ByCategorySorter implements Sorter {
    @Resource
    private IMoviesService moviesService;

    @Resource
    private ICategoriesService categoriesService;

    @Override
    public boolean canSort(String content, String sortType){
        return IMoviesService.SORT_BY_CATEGORY.equals(sortType);
    }

    @Override
    public void sort(JThequeTreeModel model){
        TreeElement root = model.getRoot();

        org.jtheque.movies.persistence.od.able.Category noCategory = categoriesService.getEmptyCategory();
        noCategory.setTitle("...");

        List<org.jtheque.movies.persistence.od.able.Category> groups = new ArrayList<org.jtheque.movies.persistence.od.able.Category>(10);
        Collection<Movie> movies = moviesService.getMovies();

        for (Movie movie : movies){
            if (movie.hasCategories()){
                for (org.jtheque.movies.persistence.od.able.Category category : movie.getCategories()){
                    if (!groups.contains(category)){
                        root.add(new Category(category.getDisplayableText()));
                        groups.add(category);
                    }

                    int index = groups.indexOf(category);

                    root.getChild(index).add(movie);
                }
            } else {
                if (!groups.contains(noCategory)){
                    root.add(new Category(noCategory.getDisplayableText()));
                    groups.add(noCategory);
                }

                int index = groups.indexOf(noCategory);

                root.getChild(index).add(movie);
            }
        }

        model.setRootElement(root);
    }
}