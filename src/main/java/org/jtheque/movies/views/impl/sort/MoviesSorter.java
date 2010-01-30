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

import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.impl.models.CategoryElement;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class MoviesSorter{
	private MoviesSorter(){
		super();
	}

	public static void sort(JThequeTreeModel model){
        TreeElement root = model.getRoot();

		root.clear();

        Collection<Category> categories = CoreUtils.<ICategoriesService>getBean("categoriesService").getCategories();
		Map<Category, CategoryElement> elements = new HashMap<Category, CategoryElement>(categories.size());

		addCategories(root, categories, elements);

		sortCategories(root);

		addMovies(root, elements);

		model.setRootElement(root);
    }

	private static void addCategories(TreeElement root, Collection<Category> categories, Map<Category, CategoryElement> elements){
		while(!categories.isEmpty()){
			Iterator<Category> iterator = categories.iterator();

			while(iterator.hasNext()){
				Category category = iterator.next();

				if(category.getParent() == null){
					CategoryElement element = new CategoryElement(category.getElementName());

					elements.put(category, element);
					root.add(element);

					iterator.remove();
				} else if(elements.containsKey(category.getParent())){
					CategoryElement element = new CategoryElement(category.getElementName());

					elements.put(category, element);
					elements.get(category.getParent()).add(element);

					iterator.remove();
				}
			}
		}
	}

	private static void sortCategories(TreeElement root){
		List<CategoryElement> categories = new ArrayList<CategoryElement>(root.getChildCount());

		for(int i = 0; i < root.getChildCount(); i++){
			categories.add((CategoryElement) root.getChild(i));
		}

		root.clear();

		Collections.sort(categories, new Comparator<CategoryElement>(){
			@Override
			public int compare(CategoryElement o1, CategoryElement o2){
				return o1.getElementName().compareToIgnoreCase(o2.getElementName());
			}
		});

		root.addAll(categories);

		for(CategoryElement element : categories){
			if(!element.isLeaf()){
				sortCategories(element);
			}
		}
	}

	private static void addMovies(TreeElement root, Map<Category, CategoryElement> elements){
		for (Movie movie : CoreUtils.<IMoviesService>getBean("moviesService").getMovies()){
            if (movie.hasCategories()){
                for (Category category : movie.getCategories()){
                    elements.get(category).add(movie);
                }
            } else {
            	root.add(movie);
            }
        }
	}
}