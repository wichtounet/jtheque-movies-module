package org.jtheque.movies.services.impl;

import org.jtheque.utils.SystemProperty;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

public class MovieFileNameFilterTest {
    private final FileFilter filter = new MovieFileNameFilter();

    @Test
    public void acceptPicturesFiles() {
        assertTrue(filter.accept(new File(SystemProperty.USER_DIR.get())));
        assertTrue(filter.accept(new File("test.avi")));
        assertTrue(filter.accept(new File("test.mpeg")));
        assertTrue(filter.accept(new File("test.wma")));
        assertTrue(filter.accept(new File("test.mpg")));
        assertTrue(filter.accept(new File("test.ogm")));
    }

    @Test
    public void refuseOtherFiles() {
        assertFalse(filter.accept(new File("test.jpg")));
        assertFalse(filter.accept(new File("test.exe")));
        assertFalse(filter.accept(new File("test.tiff")));
        assertFalse(filter.accept(new File("test.pdf")));
    }
}