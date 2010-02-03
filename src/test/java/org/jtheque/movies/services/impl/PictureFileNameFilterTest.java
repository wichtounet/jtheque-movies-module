package org.jtheque.movies.services.impl;

import org.jtheque.core.utils.SystemProperty;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

import static org.junit.Assert.*;

public class PictureFileNameFilterTest {
    private final FileFilter filter = new PictureFileNameFilter();
    
    @Test
    public void acceptPicturesFiles(){
        assertTrue(filter.accept(new File(SystemProperty.USER_DIR.get())));
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
