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

import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingBuildedDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.services.impl.cleaners.NameCleaner;
import org.jtheque.movies.views.able.ICleanMovieView;
import org.jtheque.movies.views.impl.actions.clean.AcValidateCleanViewAction;
import org.jtheque.movies.views.impl.panel.containers.CleanerContainer;
import org.jtheque.utils.ui.GridBagUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A view implementation to select the options to clean the name of movies.
 *
 * @author Baptiste Wicht
 */
public final class CleanMovieView extends SwingBuildedDialogView<IModel> implements ICleanMovieView {
    private final Collection<CleanerContainer> cleanerContainers;
    private final Collection<Movie> movies = new ArrayList<Movie>(25);

    /**
     * Construct a new CleanMovieView.
     *
     * @param cleaners       The name cleaners.
     */
    public CleanMovieView(Collection<NameCleaner> cleaners){
        super();

        cleanerContainers = new ArrayList<CleanerContainer>(cleaners.size());

        for (NameCleaner p : cleaners){
            cleanerContainers.add(new CleanerContainer(p));
        }

		build();
    }

    @Override
    protected void initView(){
        setTitleKey("movie.clean.title");
        setResizable(false);
    }

    @Override
    protected void buildView(PanelBuilder builder){
        builder.addI18nLabel("movie.clean.options", builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));

        int i = 1;

        for (CleanerContainer container : cleanerContainers){
            builder.add(container, builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL));
        }

        builder.addButtonBar(builder.gbcSet(0, ++i, GridBagUtils.HORIZONTAL),
                new AcValidateCleanViewAction(), getCloseAction("movie.auto.actions.cancel"));
    }

    @Override
    public void clean(Movie movie){
        movies.clear();
        movies.add(movie);
        display();
    }

    @Override
    public Collection<Movie> getMovies(){
        return movies;
    }

    @Override
    public void clean(Collection<Movie> movies){
        this.movies.clear();
        this.movies.addAll(movies);
        display();
    }

    @Override
    public Collection<NameCleaner> getSelectedCleaners(){
        Collection<NameCleaner> cleaners = new ArrayList<NameCleaner>(5);

        for (CleanerContainer container : cleanerContainers){
            if (container.isSelected()){
                cleaners.add(container.getCleaner());
            }
        }

        return cleaners;
    }
}