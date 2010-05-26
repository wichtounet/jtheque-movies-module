package org.jtheque.movies;

import org.jtheque.states.utils.AbstractState;
import org.jtheque.states.able.State;

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
 * A movies configuration implementation.
 *
 * @author Baptiste Wicht
 */
@State(id = "jtheque-movies-config")
public final class MovieConfiguration extends AbstractState implements IMovieConfiguration {
    private static final String OPENING_SYSTEM = "opening";
    private static final String FFMPEG_LOCATION = "ffmpeg";

    @Override
    public Opening getOpeningSystem() {
        String opening = getProperty(OPENING_SYSTEM, "system");

        if (Opening.SYSTEM.getValue().equals(opening)) {
            return Opening.SYSTEM;
        } else if (Opening.VLC.getValue().equals(opening)) {
            return Opening.VLC;
        } else if (Opening.WMP.getValue().equals(opening)) {
            return Opening.WMP;
        } else {
            return Opening.SYSTEM;
        }
    }

    @Override
    public void setOpeningSystem(Opening opening) {
        setProperty(OPENING_SYSTEM, opening.getValue());
    }

    @Override
    public String getFFmpegLocation() {
        return getProperty(FFMPEG_LOCATION, "");
    }

    @Override
    public void setFFmpegLocation(String location) {
        setProperty(FFMPEG_LOCATION, location);
    }
}