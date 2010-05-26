package org.jtheque.movies;

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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovieConfigurationTest {
    @Test
    public void openingSystem() throws Exception {
        IMovieConfiguration config = new MovieConfiguration();

        assertEquals(IMovieConfiguration.Opening.SYSTEM, config.getOpeningSystem());

        config.setOpeningSystem(IMovieConfiguration.Opening.SYSTEM);

        assertEquals(IMovieConfiguration.Opening.SYSTEM, config.getOpeningSystem());

        config.setOpeningSystem(IMovieConfiguration.Opening.VLC);

        assertEquals(IMovieConfiguration.Opening.VLC, config.getOpeningSystem());

        config.setOpeningSystem(IMovieConfiguration.Opening.WMP);

        assertEquals(IMovieConfiguration.Opening.WMP, config.getOpeningSystem());
    }

    @Test
    public void testGetFFmpegLocation() throws Exception {
        IMovieConfiguration config = new MovieConfiguration();

        assertEquals("", config.getFFmpegLocation());

        config.setFFmpegLocation("Super location");

        assertEquals("Super location", config.getFFmpegLocation());

        config.setFFmpegLocation("Nobuta Wo Produce");

        assertEquals("Nobuta Wo Produce", config.getFFmpegLocation());

    }
}