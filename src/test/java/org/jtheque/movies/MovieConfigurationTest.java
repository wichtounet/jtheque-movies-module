package org.jtheque.movies;

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

import org.junit.Test;

import static org.junit.Assert.*;

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