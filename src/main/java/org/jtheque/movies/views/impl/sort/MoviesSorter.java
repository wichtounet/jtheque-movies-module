package org.jtheque.movies.views.impl.sort;

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
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.impl.models.CategoryElement;
import org.jtheque.primary.utils.views.tree.JThequeTreeModel;
import org.jtheque.primary.utils.views.tree.TreeElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A sorter for the movies tree.
 *
 * @author Baptiste Wicht
 */
public final class MoviesSorter {
    private ICategoriesService categoriesService;
    private IMoviesService moviesService;

    /**
     * Utility class, not instanciable.
     */
    public MoviesSorter(ICategoriesService categoriesService, IMoviesService moviesService) {
        super();

        this.categoriesService = categoriesService;
        this.moviesService = moviesService;
    }

    /**
     * Sort the specified tree model.
     *
     * @param model The tree model to sort.
     */
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        root.clear();

        Collection<Category> categories = categoriesService.getCategories();
        Map<Category, CategoryElement> elements = new HashMap<Category, CategoryElement>(categories.size());

        addCategories(root, categories, elements);

        sortCategories(root);

        addMovies(root, elements);

        model.setRootElement(root);
    }

    /**
     * Add the categories to the model.
     *
     * @param root       The root element of the model.
     * @param categories The categories to add to the model.
     * @param elements   The elements collection to fill.
     */
    private static void addCategories(TreeElement root, Collection<Category> categories, Map<Category, CategoryElement> elements) {
        while (!categories.isEmpty()) {
            Iterator<Category> iterator = categories.iterator();

            while (iterator.hasNext()) {
                Category category = iterator.next();

                if (category.getParent() == null) {
                    CategoryElement element = new CategoryElement(category.getElementName());

                    elements.put(category, element);
                    root.add(element);

                    iterator.remove();
                } else if (elements.containsKey(category.getParent())) {
                    CategoryElement element = new CategoryElement(category.getElementName());

                    elements.put(category, element);
                    elements.get(category.getParent()).add(element);

                    iterator.remove();
                }
            }
        }
    }

    /**
     * Sort the categories from the root.
     *
     * @param root The root element.
     */
    private static void sortCategories(TreeElement root) {
        List<CategoryElement> categories = new ArrayList<CategoryElement>(root.getChildCount());

        for (int i = 0; i < root.getChildCount(); i++) {
            categories.add((CategoryElement) root.getChild(i));
        }

        root.clear();

        Collections.sort(categories, new Comparator<CategoryElement>() {
            @Override
            public int compare(CategoryElement o1, CategoryElement o2) {
                return o1.getElementName().compareToIgnoreCase(o2.getElementName());
            }
        });

        root.addAll(categories);

        for (CategoryElement element : categories) {
            if (!element.isLeaf()) {
                sortCategories(element);
            }
        }
    }

    /**
     * Add the movies to to the tree.
     *
     * @param root     The root element of the tree.
     * @param elements The category elements to add the movies to.
     */
    private void addMovies(TreeElement root, Map<Category, CategoryElement> elements) {
        for (Movie movie : moviesService.getMovies()) {
            if (movie.hasCategories()) {
                for (Category category : movie.getCategories()) {
                    elements.get(category).add(movie);
                }
            } else {
                root.add(movie);
            }
        }
	}
}