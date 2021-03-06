package org.jtheque.movies.services.cleaners;

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
import org.jtheque.movies.services.impl.cleaners.NumberCleaner;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Baptiste Wicht
 */
public class NumberCleanerTest {
    @Test
    public void clearName() {
        NumberCleaner cleaner = new NumberCleaner();

        assertEquals("super fichier.wma", cleaner.clearName(new MovieImpl(), "555super 3245fichier123.wma46"));
    }
}