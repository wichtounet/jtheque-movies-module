package org.jtheque.movies.services.impl;

import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileFilter;

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

import static org.junit.Assert.*;

public class PictureFileNameFilterTest {
    private final FileFilter filter = new PictureFileNameFilter();
    
    @Test
    public void acceptPicturesFiles(){
        assertTrue(filter.accept(FileSystemView.getFileSystemView().getDefaultDirectory()));
        assertTrue(filter.accept(new File("test.jpg")));
        assertTrue(filter.accept(new File("test.png")));
        assertTrue(filter.accept(new File("test.jpeg")));
        assertTrue(filter.accept(new File("test.gif")));
    }

    @Test
    public void refuseOtherFiles(){
        assertFalse(filter.accept(new File("test.avi")));
        assertFalse(filter.accept(new File("test.exe")));
        assertFalse(filter.accept(new File("test.tiff")));
        assertFalse(filter.accept(new File("test.pdf")));
    }
}
