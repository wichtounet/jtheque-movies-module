package org.jtheque.movies.services.parsers;

import org.apache.log4j.Logger;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.impl.parsers.BetweenCharCategoryParser;
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

/**
 * @author Baptiste Wicht
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class BetweenCharCategoryParserTest {
	@Resource
    private BetweenCharCategoryParser parser;

    private static File f;

    @Before
    public void setUp(){
        f = new File(" {cat1} asdf {cat2}.txt");

        try {
            f.createNewFile();
        } catch (IOException e){
			Logger.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @AfterClass
    public static void release(){
        f.delete();
    }

    @Test
    public void initOK(){
        assertNotNull(parser);
		assertFalse(parser.hasCustomView());
    }

    @Test
	public void testNotExistingFile(){
		File f2 = new File(" not existing file.txt");

		assertFalse(f2.exists());

		parser.parseFilePath(f2);

		assertEquals(0, parser.getExtractedCategories().size());
	}

    @Test
    public void getExtractedCategories(){
        parser.parseFilePath(f);
        assertEquals(2, parser.getExtractedCategories().size());

        for (Category c : parser.getExtractedCategories()){
            if (!"cat1".equals(c.getTitle()) && !"cat2".equals(c.getTitle())){
                fail();
            }
        }
    }

    @Test
    public void clearFileName(){
        assertEquals("  asdf .txt", parser.clearFileName(f.getName()));
    }
}