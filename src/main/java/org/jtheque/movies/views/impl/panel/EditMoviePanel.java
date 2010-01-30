package org.jtheque.movies.views.impl.panel;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyTextField;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.FilthyPanelBuilder;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.movies.views.able.ICategoriesView;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.actions.clean.CleanMovieAction;
import org.jtheque.movies.views.impl.actions.movies.SaveMovieAction;
import org.jtheque.movies.views.impl.actions.movies.GetInformationsAction;
import org.jtheque.movies.views.impl.actions.movies.image.EditImageAction;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.fb.MovieFormBean;
import org.jtheque.primary.view.impl.actions.principal.CancelPrincipalAction;
import org.jtheque.primary.view.impl.models.NotesComboBoxModel;
import org.jtheque.primary.view.impl.renderers.NoteComboRenderer;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.text.MaskFormatter;
import java.awt.Color;
import java.awt.Insets;
import java.text.ParseException;
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
    private FilthyTextField fieldTitle;
	private FilthyFormattedTextField fieldDuration;
	private FilthyFormattedTextField fieldResolution;
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
		addInformationsField(builder);
        addNoteField(builder);

        builder.add(categoriesView.getImpl(), builder.gbcSet(0, 5, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, -1, 1.0, 1.0));

        builder.setDefaultInsets(new Insets(2, 5, 2, 3));

        PanelBuilder buttons = builder.addPanel(builder.gbcSet(0, 6, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START, 0, 0, 1.0, 0.0));

        buttons.addButton(new SaveMovieAction(), buttons.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_TRAILING, 1.0, 1.0));
        buttons.addButton(new CancelPrincipalAction("movie.actions.cancel", "movieController"),
                buttons.gbcSet(1, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING));
    }

	/**
     * Add the field for the title.
     *
     * @param builder     The builder of the view.
     */
    private void addTitleField(PanelBuilder builder){
        builder.addI18nLabel(Movie.TITLE, builder.gbcSet(0, 0));

        fieldTitle = builder.add(new FilthyTextField(FIELD_COLUMNS), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1, 1.0, 0.0));
        ConstraintManager.configure(fieldTitle.getTextField(), Movie.TITLE);

        builder.addButton(new CleanMovieAction(), builder.gbcSet(3, 0));
        builder.addButton(new EditImageAction(), builder.gbcSet(4, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 0, 1));
    }

    /**
     * Add the field for the file.
     *
     * @param builder The builder of the view.
     */
    private void addFileField(PanelBuilder builder){
        builder.addI18nLabel(Movie.FILE, builder.gbcSet(0, 1));

        fieldFile = builder.add(new FilthyFileChooserPanel(false), builder.gbcSet(1, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1));
		fieldFile.setFilesOnly();
		ConstraintManager.configure(fieldFile.getTextField(), Movie.FILE);
    }

	private void addInformationsField(PanelBuilder parent){
		parent.addI18nLabel(Movie.DURATION, parent.gbcSet(0, 2));
		parent.addI18nLabel(Movie.RESOLUTION, parent.gbcSet(0, 3));

		try {
			fieldDuration = new FilthyFormattedTextField(new MaskFormatter("##:##:##.###"));
            fieldDuration.setText("00:00:00:000");
			fieldDuration.getTextField().setColumns(10);
        	parent.add(fieldDuration, parent.gbcSet(1, 2));
		} catch (ParseException e){
			CoreUtils.getLogger(getClass()).error(e);
		}

		try {
			fieldResolution = new FilthyFormattedTextField(new MaskFormatter("####x####"));
            fieldResolution.setText("0000x0000");
			fieldResolution.getTextField().setColumns(10);
        	parent.add(fieldResolution, parent.gbcSet(1, 3));
		} catch (ParseException e){
			CoreUtils.getLogger(getClass()).error(e);
		}

        PanelBuilder builder =
                parent.addPanel(parent.gbcSet(2, 2, GridBagUtils.HORIZONTAL, GridBagUtils.LINE_START, 2, 2, 1.0, 0.0));

		builder.getPanel().setBackground(Color.blue);

        builder.addButton(new GetInformationsAction(this),
                builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1.0, 1.0));
	}

    /**
     * Add the field for the note.
     *
     * @param builder The builder of the view.
     */
    private void addNoteField(PanelBuilder builder){
        builder.addI18nLabel(Movie.NOTE, builder.gbcSet(0, 4));
        
        modelNotes = new NotesComboBoxModel();

        JComboBox box = new JComboBox(modelNotes);
        box.setRenderer(new NoteComboRenderer());

        box.setOpaque(false);
        box.setBackground(Managers.getManager(IResourceManager.class).getColor("filthyInputColor"));

        UIManager.put("ComboBox.selectionBackground", Color.black);

        for (int i = 0; i < box.getComponentCount(); i++) {
            if (box.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) box.getComponent(i)).setBorderPainted(false);
            }
        }

        builder.add(box, builder.gbcSet(1, 4, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 2, 1));
    }

    @Override
    public void setMovie(Movie movie){
        fieldTitle.setText(movie.getTitle());
        fieldFile.setFilePath(movie.getFile());
        fieldDuration.setText(movie.getDuration() == null ? "00:00:00.000" : movie.getDuration().toString());
        fieldResolution.setText(movie.getResolution() == null ? "000x000" : movie.getResolution().toString());
        modelNotes.setSelectedItem(movie.getNote());

        categoriesView.reload(movie);
    }

    @Override
    public void validate(Collection<JThequeError> errors){
		ConstraintManager.validate(Movie.TITLE, fieldTitle.getText(), errors);
		ConstraintManager.validate(Movie.FILE, fieldFile.getFilePath(), errors);
		
        categoriesView.validate(errors);
    }

    @Override
    public IMovieFormBean fillMovieFormBean(){
        IMovieFormBean fb = new MovieFormBean();

        fb.setTitle(fieldTitle.getText());
        fb.setFile(fieldFile.getFilePath());
        fb.setResolution(new Resolution(fieldResolution.getText()));
        fb.setDuration(new PreciseDuration(fieldDuration.getText()));

        if (modelNotes.getSelectedNote() != null){
            fb.setNote(modelNotes.getSelectedNote());
        } else {
            fb.setNote(DaoNotes.getInstance().getNote(DaoNotes.NoteType.UNDEFINED));
        }

        categoriesView.fillFilm(fb);

        return fb;
    }

    public String getFilePath() {
        return fieldFile.getFilePath();
    }

    public void setResolution(Resolution resolution) {
        fieldResolution.setText(resolution.toString());
    }

    public void setDuration(PreciseDuration duration) {
        fieldDuration.setText(duration.toString());
    }
}