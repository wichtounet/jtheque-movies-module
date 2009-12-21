package org.jtheque.movies.views.impl.panel;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyFileChooserPanel;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyTextField;
import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.FilthyPanelBuilder;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.able.ICategoriesView;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.actions.clean.AcCleanMovie;
import org.jtheque.movies.views.impl.actions.movies.AcCancel;
import org.jtheque.movies.views.impl.actions.movies.AcSaveMovie;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.fb.MovieFormBean;
import org.jtheque.primary.view.impl.models.NotesComboBoxModel;
import org.jtheque.primary.view.impl.renderers.NoteComboRenderer;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import java.awt.Color;
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
 * The panel to edit a movie.
 *
 * @author Baptiste Wicht
 */
public final class EditMoviePanel extends MoviePanel {
    private static final int MAX_LENGTH = 150;

    private FilthyTextField fieldTitle;
    private FilthyFileChooserPanel fieldFile;

    private NotesComboBoxModel modelNotes;

    private static final int FIELD_COLUMNS = 25;

    private final ICategoriesView categoriesView;

    /**
     * Construct a new EditMoviePanel.
     */
    public EditMoviePanel(){
        super(IMovieView.EDIT_VIEW);

        setOpaque(false);

        categoriesView = new JPanelCategories();

        PanelBuilder builder = new FilthyPanelBuilder(this);

        setBorder(Borders.createEmptyBorder(0, 0, 0, 3));

        addTitleField(builder);
        addFileField(builder);
        addNoteField(builder);

        builder.add(categoriesView.getImpl(), builder.gbcSet(0, 3, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, -1, 1.0, 1.0));

        builder.setDefaultInsets(new Insets(2, 3, 2, 3));

        PanelBuilder buttons = builder.addPanel(builder.gbcSet(0, 4, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, 0, 0, 1.0, 0.0));

        buttons.addButton(new AcSaveMovie(), builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING, 1.0, 1.0));
        buttons.addButton(new AcCancel(), builder.gbcSet(1, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING));
    }

    /**
     * Add the field for the title.
     *
     * @param builder     The builder of the view.
     */
    private void addTitleField(PanelBuilder builder){
        builder.addI18nLabel("movie.title", builder.gbcSet(0, 0));

        fieldTitle = builder.add(new FilthyTextField(FIELD_COLUMNS), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, -1, 1, 1.0, 0.0));
        SwingUtils.addFieldLengthLimit(fieldTitle.getTextField(), 100);

        builder.addButton(new AcCleanMovie(), builder.gbcSet(2, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));
    }

    /**
     * Add the field for the file.
     *
     * @param builder The builder of the view.
     */
    private void addFileField(PanelBuilder builder){
        builder.addI18nLabel("movie.file", builder.gbcSet(0, 1));

        fieldFile = builder.add(new FilthyFileChooserPanel(false), builder.gbcSet(1, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));
    }

    /**
     * Add the field for the note.
     *
     * @param builder The builder of the view.
     */
    private void addNoteField(PanelBuilder builder){
        builder.addI18nLabel("movie.note", builder.gbcSet(0, 2));
        
        modelNotes = new NotesComboBoxModel();

        JComboBox box = new JComboBox(modelNotes);
        box.setRenderer(new NoteComboRenderer());

        box.setOpaque(false);
        box.setBackground(Managers.getManager(IResourceManager.class).getColor("filthyInputColor"));

        UIManager.put("ComboBox.selectionBackground", Color.black);

        /*box.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(Managers.getManager(IResourceManager.class).getColor("filthyInputBorderColor"), 2),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));*/

        for (int i = 0; i < box.getComponentCount(); i++) {
            if (box.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) box.getComponent(i)).setBorderPainted(false);
            }
        }

        builder.add(box, builder.gbcSet(1, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 2, 1));
    }

    @Override
    public void setMovie(Movie movie){
        fieldTitle.setText(movie.getTitle());
        fieldFile.setFilePath(movie.getFile());
        modelNotes.setSelectedItem(movie.getNote());

        categoriesView.reload(movie);
    }

    @Override
    public void validate(Collection<JThequeError> errors){
        ValidationUtils.rejectIfEmpty(fieldTitle.getText(), "movie.title", errors);
        ValidationUtils.rejectIfLongerThan(fieldTitle.getText(), "movie.title", MAX_LENGTH, errors);

        ValidationUtils.rejectIfEmpty(fieldFile.getFilePath(), "movie.file", errors);
        ValidationUtils.rejectIfLongerThan(fieldFile.getFilePath(), "movie.file", MAX_LENGTH, errors);

        categoriesView.validate(errors);
    }

    @Override
    public IMovieFormBean fillMovieFormBean(){
        IMovieFormBean fb = new MovieFormBean();

        fb.setTitle(fieldTitle.getText());
        fb.setFile(fieldFile.getFilePath());

        if (modelNotes.getSelectedNote() != null){
            fb.setNote(modelNotes.getSelectedNote());
        } else {
            fb.setNote(DaoNotes.getInstance().getNote(DaoNotes.NoteType.UNDEFINED));
        }

        categoriesView.fillFilm(fb);

        return fb;
    }
}