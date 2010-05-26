package org.jtheque.movies.utils;

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

import org.jtheque.movies.persistence.od.impl.MovieImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        assertEquals(duration1, duration1);
        assertEquals(duration1, duration2);
        assertEquals(duration2, duration1);

        assertFalse(duration1.equals(duration3));
        assertFalse(duration1.equals(null));

        assertFalse(duration3.equals(duration1));
        assertFalse(duration3.equals(duration2));

        assertFalse(duration1.equals(new MovieImpl()));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(duration1.hashCode(), duration2.hashCode());
        assertFalse(duration3.hashCode() == duration2.hashCode());
        assertFalse(duration3.hashCode() == duration1.hashCode());
    }
}