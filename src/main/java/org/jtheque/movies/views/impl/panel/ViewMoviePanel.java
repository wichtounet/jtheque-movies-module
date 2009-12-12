package org.jtheque.movies.views.impl.panel;

import org.jdesktop.swingx.JXImagePanel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.components.JThequeI18nLabel;
import org.jtheque.core.managers.view.impl.components.JThequeLabel;
import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.FilthyPanelBuilder;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.models.IconListRenderer;
import org.jtheque.movies.views.impl.models.SimpleCategoriesModel;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
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

/**
 * A panel to view the movie. 
 * 
 * @author Baptiste Wicht
 */
final class ViewMoviePanel extends MoviePanel {
    private JLabel titleLabel;
    private JLabel labelFile;
    private JThequeI18nLabel labelDate;
    private JThequeI18nLabel labelSize;
    private JXImagePanel notePanel;
    
    private SimpleCategoriesModel categoriesModel;
    
    @Resource
    private Font filthyTitleFont;

    /**
     * Construct a new ViewMoviePanel. 
     * 
     */
    ViewMoviePanel() {
        super(IMovieView.VIEW_VIEW);
        
        setOpaque(false);
    }

    /**
     * Build the panel. 
     */
    @PostConstruct
    private void build() {
        IResourceManager resources = Managers.getManager(IResourceManager.class);

        Action deleteAction = resources.getAction("deleteMovieAction");
        Action editAction = resources.getAction("editMovieAction");
        Action openAction = resources.getAction("openMovieAction");

        PanelBuilder builder = new FilthyPanelBuilder(this);

        setBorder(Borders.createEmptyBorder(0, 0, 0, 3));

        PanelBuilder title = builder.addPanel(builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, 0, 1, 1.0, 0.0));
        
        titleLabel = title.add(new JThequeLabel("", filthyTitleFont.deriveFont(18f), Color.white), 
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, 1.0, 0.0));
        
        JButton button = title.addButton(openAction, builder.gbcSet(1, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING, 0, 1, 1.0, 0.0));
        button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont(14f));
        
        builder.setDefaultInsets(new Insets(2, 3, 2, 3));

        PanelBuilder buttons = builder.addPanel(builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));

        buttons.addButton(editAction, buttons.gbcSet(0, 0));
        buttons.addButton(deleteAction, buttons.gbcSet(1, 0));
        
        addFileField(builder);
        addNoteField(builder);
        addCategoriesView(builder);
        addFileInformations(builder);
    }

    /**
	 * Add the field for the file. 
	 *
	 * @param builder The builder of the panel. 
	 */
    private void addFileField(PanelBuilder builder) {
        builder.addI18nLabel("movie.file", Font.BOLD, builder.gbcSet(0, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, -1, 1));
        
        labelFile = builder.addLabel("", builder.gbcSet(1, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1));
    }

	/**
	 * Add the field for the note. 
	 *
	 * @param builder The builder of the panel. 
	 */
    private void addNoteField(PanelBuilder builder) {
        builder.addI18nLabel("movie.note", Font.BOLD, builder.gbcSet(0, 3, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, -1, 1));
        
        notePanel = builder.add(new JXImagePanel(), builder.gbcSet(1, 3, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));
        notePanel.setOpaque(false);
    }

	/**
	 * Add the categories view. 
	 *
	 * @param builder The builder of the panel. 
	 */
    private void addCategoriesView(PanelBuilder builder) {
        categoriesModel = new SimpleCategoriesModel();

        ListCellRenderer renderer = new IconListRenderer(
                Managers.getManager(IResourceManager.class).getIcon(IMoviesModule.IMAGES_BASE_NAME, "box", ImageType.PNG)); 
                
        JList list = builder.addList(categoriesModel, renderer, builder.gbcSet(0, 4, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 1, 1.0, 1.0));
        list.setValueIsAdjusting(true);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Add the file informations to the panel. 
     * 
     * @param builder The builder of the panel. 
     */
    private void addFileInformations(PanelBuilder builder) {
        labelDate = builder.addI18nLabel("", builder.gbcSet(0, 5, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));
        labelSize = builder.addI18nLabel("", builder.gbcSet(0, 6, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));
    }

    @Override
    public void setMovie(Movie movie) {
        titleLabel.setText(Managers.getManager(ILanguageManager.class).getMessage("movie.view.movie.title", movie.getDisplayableText()));
        labelFile.setText(movie.getFile());
        notePanel.setImage(DaoNotes.getInstance().getImage(movie.getNote()));
        
        labelDate.setTextKey("movie.view.file.date", movie.getFileLastModifiedDate());
        labelSize.setTextKey("movie.view.file.size", movie.getFileSize());
        
        categoriesModel.clear();
        categoriesModel.addElements(movie.getCategories());
    }

    @Override
    public void validate(Collection<JThequeError> errors) {
        //Nothing to fill
    }

    @Override
    public IMovieFormBean fillMovieFormBean() {
        return null;
    }
}