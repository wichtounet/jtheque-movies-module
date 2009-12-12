package org.jtheque.movies.views.impl;

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

import org.jdesktop.swingx.JXTree;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.components.JThequeI18nLabel;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyCardPanel;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyPanel;
import org.jtheque.core.managers.view.impl.components.panel.CardPanel;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.FilthyPanelBuilder;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.controllers.able.IMovieController;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.able.IMoviesService;
import org.jtheque.movies.views.able.IMovieView;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.movies.views.impl.models.FilthyCellRenderer;
import org.jtheque.movies.views.impl.models.able.IMoviesModel;
import org.jtheque.movies.views.impl.panel.MoviePanel;
import org.jtheque.primary.view.able.ToolbarView;
import org.jtheque.primary.view.impl.components.panels.PrincipalDataPanel;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.sort.SortManager;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.ImageUtils;
import org.jtheque.utils.ui.SizeTracker;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Collection;

/**
 * Panel to display movies. 
 *
 * @author Baptiste Wicht
 */
public final class MovieView extends PrincipalDataPanel<IMoviesModel> implements CurrentObjectListener, IMovieView {
    private static final long serialVersionUID = -461914454879477388L;
    
    private JXTree treeMovies;

    private CardPanel<MoviePanel> layeredPanel;

    private static final double LIST_COLUMN = 0.4;
    
    @Resource
    private Font filthyTitleFont;
    
    @Resource
    private LinearGradientPaint backgroundGradient;
    
    @Resource
    private IMovieController movieController;

    private Image gradientImage;
    private final BufferedImage light = Managers.getManager(IResourceManager.class).getImage(IMoviesModule.IMAGES_BASE_NAME, "light", ImageType.PNG);
    
    private final SizeTracker tracker = new SizeTracker(this);
    private final MoviePanel[] panels;

    /**
     * Construct a new MovieView with some movie panels to display. 
     * 
     * @param panels The panels to display in the view. 
     */
    public MovieView(MoviePanel[] panels) {
        super();

        this.panels = ArrayUtils.copyOf(panels);
    }

    /**
     * Build the view. 
     */
    @PostConstruct
    private void build(){
        PanelBuilder builder = new FilthyPanelBuilder(this);
        
        buildPanelList(builder);
        buildPanelMovie(builder);

        treeMovies.addTreeSelectionListener(movieController);
        
        getModel().addDisplayListListener(this);
        getModel().addCurrentObjectListener(this);
        
        selectFirst();
    }

    /**
     * Build the internal panel list.
     * 
     * @param parent The parent builder. 
     */
    private void buildPanelList(PanelBuilder parent) {
        BorderLayout layout = new BorderLayout();
        
        layout.setHgap(2);
        layout.setVgap(2);
        
        JPanel panelList = new FilthyPanel(layout);
        
        panelList.setMinimumSize(new Dimension(165, 400));
        
        panelList.add(new JThequeI18nLabel("movie.panel.list.title", filthyTitleFont, Color.white), BorderLayout.NORTH);
        
        addTree(panelList);
        
        PanelBuilder panelButtons = new FilthyPanelBuilder();
        
        panelButtons.addI18nLabel("movie.panel.list.new", Font.BOLD, parent.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, LIST_COLUMN, 0.0));
        
        Action addAction = Managers.getManager(IResourceManager.class).getAction("newMovieAction");
        Action autoAddAction = Managers.getManager(IResourceManager.class).getAction("autoAddMovieAction");
        
        panelButtons.addButton(addAction, parent.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, LIST_COLUMN, 0.0));
        panelButtons.addButton(autoAddAction, parent.gbcSet(0, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 1, 1, LIST_COLUMN, 0.0, 10, 0));
        
        panelList.add(panelButtons.getPanel(), BorderLayout.SOUTH);
        
        parent.setDefaultInsets(new Insets(2, 2, 2, 5));
        
        parent.add(panelList, parent.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, LIST_COLUMN, 1.0));
        
        parent.setDefaultInsets(new Insets(2, 2, 2, 2));
    }

    /**
     * Add the tree to the view. 
     * 
     * @param listPanel The panel to add the tree to. 
     */
    private void addTree(Container listPanel) {
        SortManager sorter = new SortManager();

        setTreeModel(sorter.createInitialModel(IMoviesService.DATA_TYPE));

        sort(IMoviesService.SORT_BY_CATEGORY);

        treeMovies = new JXTree(getTreeModel());
        treeMovies.setRootVisible(false);
        treeMovies.setShowsRootHandles(true);
        treeMovies.setOpaque(false);
        treeMovies.setBorder(Borders.EMPTY_BORDER);
        treeMovies.setCellRenderer(new FilthyCellRenderer());
        treeMovies.putClientProperty("JTree.lineStyle", "None");

        JScrollPane pane = new JScrollPane(treeMovies);
        pane.setOpaque(false);
        pane.setBorder(Borders.createEmptyBorder(0, 0, 0, 5));
        pane.getViewport().setOpaque(false);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        listPanel.add(pane, BorderLayout.CENTER);
    }

    /**
     * Build the internal panel film.
     * 
     * @param parent The parent builder. 
     */
    private void buildPanelMovie(PanelBuilder parent) {
        layeredPanel = new FilthyCardPanel<MoviePanel>();

        for(MoviePanel panel : panels){
            layeredPanel.addLayer(panel, panel.getKey());
        }
        
        setDisplayedView(VIEW_VIEW);
        
        parent.add(layeredPanel, parent.gbcSet(1, 0, GridBagUtils.BOTH, GridBagUtils.FIRST_LINE_START, 1 - LIST_COLUMN, 1.0));
    }
    
    @Override
    public void objectChanged(ObjectChangedEvent event) {
        layeredPanel.getCurrentLayer().setMovie((Movie)event.getObject());
    }

    @Override
    public IMovieFormBean fillMovieFormBean() {
        return layeredPanel.getCurrentLayer().fillMovieFormBean();
    }

    @Override
    public String getDataType() {
        return IMoviesService.DATA_TYPE;
    }

    @Override
    protected JXTree getTree() {
        return treeMovies;
    }

    @Override
    public JComponent getImpl() {
        return this;
    }

    @Override
    public Integer getPosition() {
        return 1;
    }

    @Override
    public String getTitleKey() {
        return "data.titles.movie";
    }

    @Override
    public void clear() {
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        layeredPanel.getCurrentLayer().validate(errors);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public Iterable<Movie> getDisplayList() {
        return getModel().getDisplayList();
    }

    @Override
    @Deprecated
    public ToolbarView getToolbarView() {
        return null;
    }
    
    @Override
    public void setDisplayedView(String view){
        layeredPanel.displayLayer(view);
    }

    @Override
    public MoviePanel getCurrentView() {
        return layeredPanel.getCurrentLayer();
    }

    @Override
    public void selectFirst() {
        treeMovies.setSelectionRow(2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isVisible()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;

        if (gradientImage == null || tracker.hasSizeChanged()) {
            gradientImage = ImageUtils.createCompatibleImage(getWidth(), getHeight());
            Graphics2D g2d = (Graphics2D) gradientImage.getGraphics();
            Composite composite = g2.getComposite();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setPaint(backgroundGradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            
            g2d.drawImage(light, 0, 0, getWidth(), light.getHeight(), null);
            g2d.setComposite(composite);
            g2d.dispose();
        }

        g2.drawImage(gradientImage, 0, 0, null);
        
        tracker.updateSize();
    }
}