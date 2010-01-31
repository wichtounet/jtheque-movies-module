package org.jtheque.movies.utils;

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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PreciseDurationTest {
    private PreciseDuration duration1;
    private PreciseDuration duration2;
    private PreciseDuration duration3;

    @Before
    public void setUp() {
        duration1 = new PreciseDuration("01:49:22.800");
        duration2 = new PreciseDuration("01:49:22.800");
        duration3 = new PreciseDuration("12:49:22.800");
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("01:49:22.800", duration1.toString());
        assertEquals("01:49:22.800", duration2.toString());
        assertEquals("12:49:22.800", duration3.toString());
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(duration1, duration2);
        assertEquals(duration2, duration1);

        assertFalse(duration1.equals(duration3));
        assertFalse(duration1.equals(null));

        assertFalse(duration3.equals(duration1));
        assertFalse(duration3.equals(duration2));

    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(duration1.hashCode(), duration2.hashCode());
        assertFalse(duration3.hashCode() == duration2.hashCode());
        assertFalse(duration3.hashCode() == duration1.hashCode());
    }
}