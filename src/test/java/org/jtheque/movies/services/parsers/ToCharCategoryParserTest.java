package org.jtheque.movies.services.parsers;

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

import org.apache.log4j.Logger;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.impl.parsers.ToCharCategoryParser;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Baptiste Wicht
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class ToCharCategoryParserTest {
    @Resource
    private ToCharCategoryParser parser;

    private static File f;

    @Before
    public void setUp() {
        f = new File("cat 1-asdf {cat2}.txt");

        try {
            f.createNewFile();
        } catch (IOException e) {
            Logger.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @AfterClass
    public static void release() {
        f.delete();
    }

    @Test
    public void initOK() {
        assertNotNull(parser);
        assertFalse(parser.hasCustomView());
        assertNull(parser.getCustomView());
    }

    @Test
    public void testNotExistingFile() {
        f = new File(" not existing file.txt");

        assertFalse(f.exists());

        parser.parseFilePath(f);

        assertEquals(0, parser.getExtractedCategories().size());
    }

    @Test
    public void getExtractedCategories() {
        parser.parseFilePath(f);
        assertEquals(1, parser.getExtractedCategories().size());

        for (Category c : parser.getExtractedCategories()) {
            if (!"cat 1".equals(c.getTitle())) {
                fail();
            }
        }
    }

    @Test
    public void getExtractedCategoriesWithExisting() {
        parser.parseFilePath(f);
        parser.parseFilePath(f);

        assertEquals(2, parser.getExtractedCategories().size());
    }

    @Test
    public void clearFileName() {
        assertEquals("asdf {cat2}.txt", parser.clearFileName(f.getName()));
    }
}