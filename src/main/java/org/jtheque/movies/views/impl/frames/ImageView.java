package org.jtheque.movies.views.impl.frames;

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

import org.jtheque.images.able.IImageService;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.controllers.impl.ImageController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.PictureFileNameFilter;
import org.jtheque.movies.views.able.IImageView;
import org.jtheque.movies.views.impl.actions.movies.image.GenerateFileImageAction;
import org.jtheque.movies.views.impl.actions.movies.image.GenerateRandomImageAction;
import org.jtheque.movies.views.impl.actions.movies.image.GenerateTimeImageAction;
import org.jtheque.movies.views.impl.actions.movies.image.ValidateImageViewAction;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.filthy.FilthyFileChooserPanel;
import org.jtheque.ui.utils.filthy.FilthyFormattedTextField;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.GridBagUtils;

import org.jdesktop.swingx.JXImagePanel;

import javax.annotation.Resource;
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

    @Resource
    private ImageController imageController;

    @Resource
    private IMoviesModule moviesModule;

    @Override
    protected void initView() {
        setTitleKey("movie.image.title");
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        addFFmpegActions(builder);
        addFileActions(builder);

        imagePanel = builder.add(new JXImagePanel(), builder.gbcSet(0, 2, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 0, -1, 1.0, 1.0));
        imagePanel.setOpaque(false);

        builder.addButtonBar(builder.gbcSet(0, 3, GridBagUtils.HORIZONTAL),
                new ValidateImageViewAction(imageController), getCloseAction("movie.image.actions.cancel"));
    }

    /**
     * Add the FFMpeg actions to the builder.
     *
     * @param parent The panel builder.
     */
    private void addFFmpegActions(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.ABOVE_BASELINE_LEADING, 3, 1));
        builder.setI18nTitleBorder("movie.image.ffmpeg");

        builder.addButton(new GenerateRandomImageAction(imageController), builder.gbcSet(0, 0, GridBagUtils.NONE, 0, 1));

        builder.addI18nLabel("movie.image.file.time", builder.gbcSet(0, 1));

        timeTextField = builder.add(new FilthyFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance())),
                builder.gbcSet(1, 1));
        timeTextField.getField().setColumns(5);

        builder.addButton(new GenerateTimeImageAction(imageController), builder.gbcSet(2, 1, GridBagUtils.NONE, 0, 1));
    }

    /**
     * Add file actions to the builder.
     *
     * @param parent The panel builder to add the actions to.
     */
    private void addFileActions(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));
        builder.setI18nTitleBorder("movie.image.file");

        imageChooser = builder.add(new FilthyFileChooserPanel(true), builder.gbcSet(0, 0));
        imageChooser.setTextKey("movie.image.file.location");
        imageChooser.setFileFilter(new PictureFileNameFilter());

        builder.addButton(new GenerateFileImageAction(imageController), builder.gbcSet(1, 0, GridBagUtils.NONE, 0, 1));
    }

    @Override
    public void displayMovie(Movie movie) {
        if (StringUtils.isNotEmpty(movie.getImage())) {
            String resource = moviesModule.getThumbnailFolderPath() + movie.getImage();

            getService(IImageService.class).invalidateImage(resource);

            setImage(getService(IImageService.class).getFileImage(resource));
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