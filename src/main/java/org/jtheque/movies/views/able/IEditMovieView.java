package org.jtheque.movies.views.able;

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

import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.ui.View;

/**
 * Created by IntelliJ IDEA. User: wichtounet Date: Jul 21, 2010 Time: 12:10:30 PM To change this template use File |
 * Settings | File Templates.
 */
public interface IEditMovieView extends View {
    /**
     * Return the entered file path.
     *
     * @return The entered file path.
     */
    String getFilePath();

    /**
     * Set the resolution.
     *
     * @param resolution The resolution to set.
     */
    void setResolution(Resolution resolution);

    /**
     * Set the duration.
     *
     * @param duration The duration to set.
     */
    void setDuration(PreciseDuration duration);
}
