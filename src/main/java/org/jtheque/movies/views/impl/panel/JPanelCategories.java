package org.jtheque.movies.views.impl.panel;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.core.utils.ui.FilthyPanelBuilder;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.able.ICategoriesView;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.models.CategoriesListModel;
import org.jtheque.movies.views.impl.models.IconListRenderer;
import org.jtheque.movies.views.impl.models.SimpleCategoriesModel;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.Action;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collection;

/**
 * A panel to display the categories of a movie and enable the user to edit them. 
 * 
 * @author Baptiste Wicht
 */
public final class JPanelCategories extends JPanel implements ICategoriesView {
    private static final long serialVersionUID = 447863972139498415L;
    
    private JList listCategories;
    private JList listCategoriesForFilm;
    
    private CategoriesListModel categoriesModel;
    private SimpleCategoriesModel categoriesForMovieModel;
    
    private final Action newAction;
    private final Action editAction;
    private final Action deleteAction;

    /**
     * Construct a new JPanelCategories with the given actions. 
     * 
     * @param newAction The action to create a new category. 
     * @param deleteAction The action to delete a category. 
     * @param editAction The action to edit a category. 
     */
    public JPanelCategories(Action newAction, Action editAction, Action deleteAction) {
        super();
        
        this.newAction = newAction;
        this.editAction = editAction;
        this.deleteAction = deleteAction;
        
        build();
    }

    /**
     * Build the view. This method is executed by spring after properties set. 
     * 
     */
    private void build() {
        PanelBuilder builder = new FilthyPanelBuilder(this);
        
        categoriesModel = new CategoriesListModel();
        
        double anHalf = 0.5;
        
        ListCellRenderer renderer = new IconListRenderer(
                Managers.getManager(IResourceManager.class).getIcon(IMoviesModule.IMAGES_BASE_NAME, "box", ImageType.PNG)); 
        
        listCategories = builder.addList(categoriesModel, renderer, 
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, anHalf, 1.0));
        listCategories.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        PanelBuilder buttons = builder.addPanel(builder.gbcSet(1, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, -1, 1));
        
        buttons.addButton(new AcAddToList(), builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        buttons.addButton(new AcRemoveFromList(), builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));

        categoriesForMovieModel = new SimpleCategoriesModel();
        categoriesModel.setLinkedModel(categoriesForMovieModel);
        
        listCategoriesForFilm = builder.addList(categoriesForMovieModel, renderer, 
                builder.gbcSet(2, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, -1, anHalf, 1.0));
        listCategoriesForFilm.setValueIsAdjusting(true);
        listCategoriesForFilm.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        PanelBuilder manageButtons = builder.addPanel(builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 0));
        
        manageButtons.addI18nLabel("category.view.manage", Font.BOLD, builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING));
        manageButtons.add(new JButton(newAction), builder.gbcSet(1, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING));
        manageButtons.add(new JButton(editAction), builder.gbcSet(2, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING));
        manageButtons.add(new JButton(deleteAction), builder.gbcSet(3, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING));
    }

    @Override
    public void fillFilm(IMovieFormBean fb) {
        if (categoriesForMovieModel.getSize() != 0) {
            fb.setCategories(categoriesForMovieModel.getCategories());
        }
    }
    
    @Override
    public void reload(Movie movie) {
        categoriesForMovieModel.removeAllElements();
        categoriesModel.reload();
        
        for (Category category : movie.getCategories()) {
            categoriesModel.removeElement(category);
            categoriesForMovieModel.addElement(category);
        }
    }

    @Override
    public void validate(Collection<JThequeError> errors) {
        ValidationUtils.rejectIfEmpty(listCategoriesForFilm, "movie.categories", errors);
    }

    @Override
    public Component getImpl() {
        return this;
    }
    
    /**
     * Action to add an actor to the list.
     *
     * @author Baptiste Wicht
     */
    private final class AcAddToList extends JThequeSimpleAction {
        private static final long serialVersionUID = -1335864379997427135L;
        
        /**
         * Construct a new AcAddToList action with a text ">>". 
         */
        private AcAddToList() {
            super();
            
            setText(" >> ");
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {    
            int[] selectedActors = listCategories.getSelectedIndices();
    
            Arrays.sort(selectedActors);
    
            ArrayUtils.reverse(selectedActors);
    
            for (int index : selectedActors) {
                Object actor = categoriesModel.remove(index);
                categoriesForMovieModel.addElement(actor);
            }
        }
    }
    
    /**
     * Action to remove an actor from the list.
     *
     * @author Baptiste Wicht
     */
    private final class AcRemoveFromList extends JThequeSimpleAction {
        private static final long serialVersionUID = -8163503906464833282L;
        
        /**
         * Construct a new AcRemoveFromList action with a text <<. 
         */
        private AcRemoveFromList() {
            super();
            
            setText(" << ");
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selectedActors = listCategoriesForFilm.getSelectedIndices();
    
            Arrays.sort(selectedActors);
    
            ArrayUtils.reverse(selectedActors);
    
            for (int index : listCategoriesForFilm.getSelectedIndices()) {
                Object actor = categoriesForMovieModel.remove(index);
                categoriesModel.addElement(actor);
            }
        }
    }
}