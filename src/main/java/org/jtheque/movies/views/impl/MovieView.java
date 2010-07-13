package org.jtheque.movies.views.impl;

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

import org.jtheque.errors.able.IError;
import org.jtheque.images.able.IImageService;
import org.jtheque.movies.MoviesResources;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.ICategoriesService;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.able.models.IMoviesModel;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.models.CategoryElement;
import org.jtheque.movies.views.impl.models.FilthyCellRenderer;
import org.jtheque.movies.views.impl.models.MoviesModel;
import org.jtheque.movies.views.impl.panel.MoviePanel;
import org.jtheque.movies.views.impl.sort.MoviesSorter;
import org.jtheque.persistence.able.Entity;
import org.jtheque.primary.able.controller.IPrincipalController;
import org.jtheque.primary.utils.views.listeners.CurrentObjectListener;
import org.jtheque.primary.utils.views.listeners.DisplayListListener;
import org.jtheque.primary.utils.views.listeners.ObjectChangedEvent;
import org.jtheque.primary.utils.views.tree.JThequeTreeModel;
import org.jtheque.primary.utils.views.tree.TreeElement;
import org.jtheque.ui.able.components.Borders;
import org.jtheque.ui.able.components.CardPanel;
import org.jtheque.ui.able.components.filthy.Filthy;
import org.jtheque.ui.utils.actions.ActionFactory;
import org.jtheque.ui.utils.builded.OSGIFilthyBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import org.jdesktop.swingx.JXTree;

import javax.annotation.Resource;
import javax.swing.JLabel;
import javax.swing.tree.TreePath;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.Collection;

import static org.jtheque.ui.able.components.filthy.FilthyConstants.TITLE_FONT;

/**
 * Panel to display movies.
 *
 * @author Baptiste Wicht
 */
public final class MovieView extends OSGIFilthyBuildedPanel implements CurrentObjectListener, IMovieView, DisplayListListener {
    private static final double LIST_COLUMN = 0.3;

    private final JThequeTreeModel treeModel = new JThequeTreeModel(new CategoryElement("Movies"));

    private CardPanel<MoviePanel> layeredPanel;
    private JXTree treeMovies;
    private MoviesSorter moviesSorter;

    @Resource
    private IMoviesService moviesService;

    @Resource
    private ICategoriesService categoriesService;

    @Resource
    private IPrincipalController<Movie> movieController;

    private final MoviePanel viewMoviePanel;
    private final MoviePanel editMoviePanel;

    /**
     * Create a new MovieView.
     *
     * @param viewMoviePanel The panel to view movie.
     * @param editMoviePanel The panel to edit movie. 
     */
    public MovieView(MoviePanel viewMoviePanel, MoviePanel editMoviePanel) {
        super();

        this.viewMoviePanel = viewMoviePanel;
        this.editMoviePanel = editMoviePanel;
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        setModel(new MoviesModel(moviesService));

        moviesSorter = new MoviesSorter(categoriesService, moviesService);

        buildPanelList(builder);
        buildPanelMovie(builder);

        treeMovies.addTreeSelectionListener(movieController);

        selectFirst();

        getModel().addCurrentObjectListener(this);
        getModel().addDisplayListListener(this);
    }

    /**
     * Build the internal panel list.
     *
     * @param parent The parent builder.
     */
    private void buildPanelList(I18nPanelBuilder parent) {
        parent.setDefaultInsets(new Insets(2, 2, 2, 5));

        I18nPanelBuilder builder = parent.addPanel(new BorderLayout(2, 2),
                parent.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, LIST_COLUMN, 1.0));

        parent.setDefaultInsets(new Insets(2, 2, 2, 2));

        builder.getPanel().setBorder(Borders.createEmptyBorder(10, 10, 10, 20));
        builder.getPanel().setMinimumSize(new Dimension(165, 400));

        addTitle(builder);
        addTree(builder);
        addActions(builder);
    }

    /**
     * Add the title of the tree to the builder.
     *
     * @param builder The panel builder.
     */
    private void addTitle(I18nPanelBuilder builder) {
        I18nPanelBuilder titleBuilder = builder.addPanel(BorderLayout.NORTH);

        JLabel label = titleBuilder.addI18nLabel("movie.panel.list.title",
                titleBuilder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));

        IImageService imageService = getService(IImageService.class);

        titleBuilder.addButton(
                ActionFactory.createAction("refresh", imageService.getIcon(MoviesResources.REFRESH_ICON), movieController),
                titleBuilder.gbcSet(1, 0));
        titleBuilder.addButton(
                ActionFactory.createAction("expand", imageService.getIcon(MoviesResources.EXPAND_ICON), movieController),
                titleBuilder.gbcSet(2, 0));
        titleBuilder.addButton(
                ActionFactory.createAction("collapse", imageService.getIcon(MoviesResources.COLLAPSE_ICON), movieController),
                titleBuilder.gbcSet(3, 0));

        label.setFont(TITLE_FONT.deriveFont(25.0f));
    }

    /**
     * Add the tree to the builder.
     *
     * @param builder The panel builder.
     */
    private void addTree(PanelBuilder builder) {
        moviesSorter.sort(treeModel);

        treeMovies = (JXTree) builder.addScrolledTree(treeModel,
                new FilthyCellRenderer(getService(IImageService.class)), BorderLayout.CENTER);
    }

    /**
     * Add the add actions to the view.
     *
     * @param builder The panel builder.
     */
    private void addActions(I18nPanelBuilder builder) {
        I18nPanelBuilder panelButtons = builder.addPanel(BorderLayout.SOUTH);

        panelButtons.addI18nLabel("movie.panel.list.new", Font.BOLD,
                builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, LIST_COLUMN, 0.0));

        panelButtons.addButton(ActionFactory.createAction("movie.actions.add", movieController),
                builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, LIST_COLUMN, 0.0));
        panelButtons.addButton(ActionFactory.createAction("movie.auto.actions.add", movieController),
                builder.gbcSet(0, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 1, 1, LIST_COLUMN, 0.0, 10, 0));
    }

    /**
     * Build the internal panel film.
     *
     * @param parent The parent builder.
     */
    private void buildPanelMovie(PanelBuilder parent) {
        layeredPanel = Filthy.newCardPanel();

        layeredPanel.addLayer(viewMoviePanel, viewMoviePanel.getKey());
        layeredPanel.addLayer(editMoviePanel, editMoviePanel.getKey());

        setDisplayedView(VIEW_VIEW);

        parent.add(layeredPanel, parent.gbcSet(1, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1 - LIST_COLUMN, 1.0));
    }

    @Override
    public void displayListChanged() {
        moviesSorter.sort(treeModel);
    }

    @Override
    public void validate(Collection<IError> errors) {
        layeredPanel.getCurrentLayer().validate(errors);
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        layeredPanel.getCurrentLayer().setMovie((Movie) event.getObject());
    }

    @Override
    public IMovieFormBean fillMovieFormBean() {
        return layeredPanel.getCurrentLayer().fillMovieFormBean();
    }

    @Override
    public void setDisplayedView(String view) {
        layeredPanel.displayLayer(view);
    }

    @Override
    public MoviePanel getCurrentView() {
        return layeredPanel.getCurrentLayer();
    }

    @Override
    public void select(Movie movie) {
        select(movie, treeModel.getRoot(), new TreePath(treeModel.getRoot()));
    }

    /**
     * Select the specified movie in the tree.
     *
     * @param movie The movie to select.
     * @param root  The current element to search in.
     * @param path  The current search path.
     *
     * @return true if the movie has been selected else false.
     */
    private boolean select(Movie movie, TreeElement root, TreePath path) {
        for (int i = 0; i < treeModel.getChildCount(root); i++) {
            TreeElement child = (TreeElement) treeModel.getChild(root, i);

            if (child.isCategory()) {
                treeMovies.expandRow(i);

                if (select(movie, child, path.pathByAddingChild(child))) {
                    return true;
                }

                treeMovies.collapseRow(i);
            }

            if (child instanceof Movie && ((Entity) child).getId() == movie.getId()) {
                treeMovies.setSelectionPath(path.pathByAddingChild(child));

                return true;
            }
        }

        return false;
    }

    @Override
    public void selectFirst() {
        if (treeModel.getChildCount(treeModel.getRoot()) > 0) {
            selectFirst(treeModel.getRoot(), new TreePath(treeModel.getRoot()));
        }
    }

    /**
     * Select the first movie in the tree.
     *
     * @param root The element to start the search for.
     * @param path The current search path.
     *
     * @return true if the first movie has been selected else false.
     */
    private boolean selectFirst(TreeElement root, TreePath path) {
        for (int i = 0; i < treeModel.getChildCount(root); i++) {
            TreeElement child = (TreeElement) treeModel.getChild(root, i);

            if (child.isCategory()) {
                treeMovies.expandRow(i);

                if (selectFirst(child, path.pathByAddingChild(child))) {
                    return true;
                }

                treeMovies.collapseRow(i);
            }

            if (child instanceof Movie) {
                treeMovies.setSelectionPath(path.pathByAddingChild(child));

                return true;
            }
        }

        return false;
    }

    @Override
    public void refreshData() {
        for (MoviePanel panel : layeredPanel.getLayers()) {
            panel.setMovie(getModel().getCurrentMovie());
        }
    }

    @Override
    public Movie getSelectedMovie() {
        return treeMovies.getSelectionPath() == null ? null : (Movie) treeMovies.getSelectionPath().getLastPathComponent();
    }

    @Override
    public IMoviesModel getModel() {
        return (IMoviesModel) super.getModel();
    }

    @Override
    public void expandAll() {
        treeMovies.expandAll();
    }

    @Override
    public void collapseAll() {
        treeMovies.collapseAll();
    }

    @Override
    public void resort() {
        displayListChanged();
    }

    @Override
    public int getPosition() {
        return 5;
    }

    @Override
    public String getTitleKey() {
        return "data.titles.movie";
    }
}