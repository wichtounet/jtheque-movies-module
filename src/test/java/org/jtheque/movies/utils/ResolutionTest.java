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

public class ResolutionTest {
    private Resolution resolution1;
    private Resolution resolution2;
    private Resolution resolution3;

    @Before
    public void setUp() {
        resolution1 = new Resolution("0480x0640");
        resolution2 = new Resolution("0480x0640");
        resolution3 = new Resolution("0396x0728");
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("0480x0640", resolution1.toString());
        assertEquals("0480x0640", resolution2.toString());
        assertEquals("0396x0728", resolution3.toString());
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(resolution1, resolution2);
        assertEquals(resolution2, resolution1);

        assertFalse(resolution1.equals(resolution3));
        assertFalse(resolution1.equals(null));

        assertFalse(resolution3.equals(resolution1));
        assertFalse(resolution3.equals(resolution2));

    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(resolution1.hashCode(), resolution2.hashCode());
        assertFalse(resolution3.hashCode() == resolution2.hashCode());
        assertFalse(resolution3.hashCode() == resolution1.hashCode());
    }
}