package org.jtheque.movies.views.impl.panel.players;

import javax.swing.JComponent;
import java.io.File;

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
 * A movie player specification.
 *
 * @author Baptiste Wicht
 */
public interface IMoviePlayer {
    /**
     * Stop the play.
     */
    void stop();

    /**
     * Load the specified file.
     *
     * @param f The movie file.
     */
    void load(File f);

    /**
     * Return the swing component of the player.
     *
     * @return The swing component of the player.
     */
    JComponent getComponent();
}