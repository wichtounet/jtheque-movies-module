package org.jtheque.movies.views.impl.panel;

import org.jtheque.movies.persistence.od.able.Movie;
import org.jtheque.movies.views.impl.fb.IMovieFormBean;
import org.jtheque.ui.utils.builded.OSGIFilthyBuildedPanel;

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

/**
 * A Movie panel. It's a panel for a specific mode for movies.
 *
 * @author Baptiste Wicht
 */
public abstract class MoviePanel extends OSGIFilthyBuildedPanel {
    private final String key;

    /**
     * Construct a new MoviePanel.
     *
     * @param key The key identifying the panel.
     */
    MoviePanel(String key) {
        super();

        this.key = key;
    }

    /**
     * Set the current movie.
     *
     * @param movie The current movie.
     */
    public abstract void setMovie(Movie movie);

    /**
     * Fill a movie form bean with the informations of the view.
     *
     * @return the filled movie form bean.
     */
    public abstract IMovieFormBean fillMovieFormBean();

    /**
     * Return the key of the panel.
     *
     * @return The key of the panel.
     */
    public final String getKey() {
        return key;
    }
}