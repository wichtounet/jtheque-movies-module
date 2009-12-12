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

import org.jtheque.core.utils.SystemProperty;
import org.jtheque.movies.services.impl.parsers.FolderCategoryParser;
import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.utils.io.FileUtils;
import org.junit.Test;
import org.junit.Before;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author Baptiste Wicht
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class FolderCategoryParserTest {
    @Resource
    private FolderCategoryParser parser;
    
    private static File f;
    private static File parent;
    
    @Before
    public void setUp(){
        parent = new File(SystemProperty.JAVA_IO_TMP_DIR.get());
        f = new File(parent, "test.txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        f.deleteOnExit();
        
        FileUtils.createIfNotExists(f);
        parser.parseFilePath(f);
    }
    
    @AfterClass
    public static void release(){
        f.delete();
    }
    
    @Test
    public void initOK() {
        assertNotNull(parser);
    }

    @Test
    public void getExtractedCategories() {
        assertEquals(parser.getExtractedCategories().size(), 1);
        
        for(Category c : parser.getExtractedCategories()){
            if(!parent.getName().equals(c.getTitle())){
                fail();
            }
        }
    }

    @Test
    public void clearFileName() {
        assertEquals(f.getName(), parser.clearFileName(f.getName()));
    }
}