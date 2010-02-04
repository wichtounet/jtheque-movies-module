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

import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResolutionTest {
    private Resolution resolution1;
    private Resolution resolution2;
    private Resolution resolution3;
    private Resolution resolution4;
    private Resolution resolution5;

    @Before
    public void setUp() {
        resolution1 = new Resolution("0480x0640");
        resolution2 = new Resolution("0480x0640");
        resolution3 = new Resolution("0396x0728");
        resolution4 = new Resolution("0480x0600");
        resolution5 = new Resolution("0200x0728");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyResolution(){
        new Resolution("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidResolution(){
        new Resolution("450 780");        
    }

    @Test
    public void testToString(){
        assertEquals("0480x0640", resolution1.toString());
        assertEquals("0480x0640", resolution2.toString());
        assertEquals("0396x0728", resolution3.toString());
    }

    @Test
    public void testEquals(){
        assertEquals(resolution1, resolution1);
        assertEquals(resolution2, resolution2);
        assertEquals(resolution1, resolution2);
        assertEquals(resolution2, resolution1);

        assertFalse(resolution1.equals(resolution3));
        assertFalse(resolution1.equals(null));

        assertFalse(resolution3.equals(resolution1));
        assertFalse(resolution3.equals(resolution2));

        assertFalse(resolution3.equals(new MovieImpl()));

        assertFalse(resolution2.equals(resolution4));
        assertFalse(resolution5.equals(resolution3));
    }

    @Test
    public void testHashCode(){
        assertEquals(resolution1.hashCode(), resolution2.hashCode());
        assertFalse(resolution3.hashCode() == resolution2.hashCode());
        assertFalse(resolution3.hashCode() == resolution1.hashCode());
    }
}