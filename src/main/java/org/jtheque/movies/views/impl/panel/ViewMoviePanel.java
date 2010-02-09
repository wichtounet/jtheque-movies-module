package org.jtheque.movies.views.impl.panel;

import org.jdesktop.swingx.JXImagePanel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.components.JThequeI18nLabel;
import org.jtheque.core.managers.view.impl.components.JThequeLabel;
import org.jtheque.core.managers.view.impl.components.model.SimpleListModel;
import org.jtheque.core.managers.view.impl.components.renderers.IconListRenderer;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.FilthyPanelBuilder;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.actions.movies.DeleteMovieAction;
import org.jtheque.movies.views.impl.actions.view.PlayMovieAction;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.primary.view.impl.actions.principal.ManualEditPrincipalAction;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
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
public final class ViewMoviePanel extends MoviePanel {
    private JLabel titleLabel;
    private JLabel labelFile;

    private JThequeI18nLabel labelDate;
    private JThequeI18nLabel labelSize;
    private JThequeI18nLabel labelDuration;
    private JThequeI18nLabel labelResolution;

    private JXImagePanel notePanel;
    private JXImagePanel imagePanel;

    private SimpleListModel<Category> categoriesModel;

    private static final float TITLE_FONT_SIZE = 18f;
    private static final float BUTTON_FONT_SIZE = 14f;

    /**
     * Construct a new ViewMoviePanel.
     */
    public ViewMoviePanel() {
        super(IMovieView.VIEW_VIEW);

        build();
    }

    /**
     * Build the panel.
     */
    private void build() {
        setOpaque(false);

        PanelBuilder builder = new FilthyPanelBuilder(this);

        setBorder(Borders.createEmptyBorder(0, 0, 0, 3));

        PanelBuilder title = builder.addPanel(builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, 0, 1, 1.0, 0.0));

        Font filthyTitleFont = CoreUtils.getBean("filthyTitleFont");

        titleLabel = title.add(new JThequeLabel("", filthyTitleFont.deriveFont(TITLE_FONT_SIZE), Color.white),
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, 1.0, 0.0));

        JButton button = title.addButton(new PlayMovieAction(), builder.gbcSet(1, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING, 0, 1, 1.0, 0.0));
        button.setFont(button.getFont().deriveFont(Font.BOLD).deriveFont(BUTTON_FONT_SIZE));

        builder.setDefaultInsets(new Insets(2, 3, 2, 3));

        PanelBuilder buttons = builder.addPanel(builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));

        buttons.addButton(new ManualEditPrincipalAction("movie.actions.edit", "movieController"), buttons.gbcSet(0, 0));
        buttons.addButton(new DeleteMovieAction(), buttons.gbcSet(1, 0));

        addFileField(builder);
        addNoteField(builder);

        PanelBuilder center = builder.addPanel(builder.gbcSet(0, 4, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 1, 1.0, 1.0));

        addCategoriesView(center);
        addImagePanel(center);

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
        categoriesModel = new SimpleListModel<Category>();

        ListCellRenderer renderer = new IconListRenderer(
                Managers.getManager(IResourceManager.class).getIcon(IMoviesModule.IMAGES_BASE_NAME, "box", ImageType.PNG));

        builder.addList(categoriesModel, renderer, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, -1, 1, 1.0, 1.0));
    }

    /**
     * Add the image panel to the view.
     *
     * @param builder The panel builder.
     */
    private void addImagePanel(PanelBuilder builder) {
        imagePanel = builder.add(new JXImagePanel(), builder.gbcSet(1, 0));
        imagePanel.setOpaque(false);
    }

    /**
     * Add the file informations to the panel.
     *
     * @param builder The builder of the panel.
     */
    private void addFileInformations(PanelBuilder builder) {
        labelDate = builder.addI18nLabel("", builder.gbcSet(0, 5, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));
        labelSize = builder.addI18nLabel("", builder.gbcSet(0, 6, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));
        labelDuration = builder.addI18nLabel("", builder.gbcSet(0, 7, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));
        labelResolution = builder.addI18nLabel("", builder.gbcSet(0, 8, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));
    }

    @Override
    public void setMovie(Movie movie) {
        titleLabel.setText(CoreUtils.getMessage("movie.view.movie.title", movie.getDisplayableText()));
        labelFile.setText(movie.getFile());
        notePanel.setImage(DaoNotes.getImage(movie.getNote()));

        labelDate.setTextKey("movie.view.file.date", movie.getFileLastModifiedDate());
        labelSize.setTextKey("movie.view.file.size", movie.getFileSize());
        labelDuration.setTextKey("movie.view.file.duration", movie.getDuration());
        labelResolution.setTextKey("movie.view.file.resolution", movie.getResolution());

        if (StringUtils.isNotEmpty(movie.getImage())) {
            imagePanel.setImage(Managers.getManager(IResourceManager.class).getImage("file:" +
                    CoreUtils.<IMoviesModule>getBean("moviesModule").getThumbnailFolderPath() + movie.getImage()));
        } else {
            imagePanel.setImage(null);
        }

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