package org.jtheque.movies.views.impl.frames;

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

import org.jdesktop.swingx.JXImagePanel;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.PictureFileNameFilter;
import org.jtheque.movies.utils.TempSwingUtils;
import org.jtheque.movies.views.able.IImageView;
import org.jtheque.movies.views.impl.actions.movies.image.GenerateFileImageAction;
import org.jtheque.movies.views.impl.actions.movies.image.GenerateRandomImageAction;
import org.jtheque.movies.views.impl.actions.movies.image.GenerateTimeImageAction;
import org.jtheque.movies.views.impl.actions.movies.image.ValidateImageViewAction;
import org.jtheque.movies.views.impl.panel.FilthyFileChooserPanel;
import org.jtheque.movies.views.impl.panel.FilthyFormattedTextField;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.ImageUtils;

import javax.swing.text.NumberFormatter;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;

/**
 * A view implementation to select the image for a movie.
 *
 * @author Baptiste Wicht
 */
public final class ImageView extends SwingFilthyBuildedDialogView<IModel> implements IImageView {
    private FilthyFormattedTextField timeTextField;
    private FilthyFileChooserPanel imageChooser;

    private JXImagePanel imagePanel;

    /**
     * Construct a new ImageView.
     */
    public ImageView() {
        super();

        build();
    }

    @Override
    protected void initView() {
        setTitleKey("movie.image.title");
    }

    @Override
    protected void buildView(PanelBuilder builder) {
        addFFmpegActions(builder);
        addFileActions(builder);

        imagePanel = builder.add(new JXImagePanel(), builder.gbcSet(0, 2, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 0, -1, 1.0, 1.0));
        imagePanel.setOpaque(false);

        TempSwingUtils.addFilthyButtonBar(builder, builder.gbcSet(0, 3, GridBagUtils.HORIZONTAL),
                new ValidateImageViewAction(), getCloseAction("movie.image.actions.cancel"));
    }

    /**
     * Add the FFMpeg actions to the builder.
     *
     * @param parent The panel builder.
     */
    private void addFFmpegActions(PanelBuilder parent) {
        PanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.ABOVE_BASELINE_LEADING, 3, 1));
        builder.getPanel().setBorder(TempSwingUtils.createFilthyTitledBorder("movie.image.ffmpeg"));

        builder.addButton(new GenerateRandomImageAction(), builder.gbcSet(0, 0, GridBagUtils.NONE, 0, 1));

        builder.addI18nLabel("movie.image.file.time", builder.gbcSet(0, 1));

        timeTextField = builder.add(new FilthyFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance())),
                builder.gbcSet(1, 1));
        timeTextField.getTextField().setColumns(5);

        builder.addButton(new GenerateTimeImageAction(), builder.gbcSet(2, 1, GridBagUtils.NONE, 0, 1));
    }

    /**
     * Add file actions to the builder.
     *
     * @param parent The panel builder to add the actions to.
     */
    private void addFileActions(PanelBuilder parent) {
        PanelBuilder builder = parent.addPanel(parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));
        builder.getPanel().setBorder(TempSwingUtils.createFilthyTitledBorder("movie.image.file"));
        builder.getPanel().setOpaque(false);

        imageChooser = builder.add(new FilthyFileChooserPanel(true), builder.gbcSet(0, 0));
        imageChooser.setTextKey("movie.image.file.location");
        imageChooser.setFileFilter(new PictureFileNameFilter());

        builder.addButton(new GenerateFileImageAction(), builder.gbcSet(1, 0, GridBagUtils.NONE, 0, 1));
    }

    @Override
    public void displayMovie(Movie movie) {
        if (StringUtils.isNotEmpty(movie.getImage())) {
            String resource = "file:" +
                    CoreUtils.<IMoviesModule>getBean("moviesModule").getThumbnailFolderPath() + movie.getImage();

            setImage(ImageUtils.openCompatibleImage(
                    Managers.getManager(IResourceManager.class).getResourceAsStream(resource)));
        }
    }

    @Override
    public String getTime() {
        return timeTextField.getText();
    }

    @Override
    public String getImagePath() {
        return imageChooser.getFilePath();
    }

    @Override
    public void setImage(BufferedImage image) {
        imagePanel.setImage(image);
    }

    @Override
    public BufferedImage getImage() {
        return (BufferedImage) imagePanel.getImage();
	}
}