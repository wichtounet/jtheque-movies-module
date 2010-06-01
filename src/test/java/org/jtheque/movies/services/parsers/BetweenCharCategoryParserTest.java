package org.jtheque.movies.services.parsers;

import org.jtheque.movies.persistence.od.able.Category;
import org.jtheque.movies.services.impl.parsers.BetweenCharCategoryParser;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

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
    public void setUp() {
        f = new File(" {cat1} asdf {cat2}.txt");

        try {
            f.createNewFile();
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
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
        File f2 = new File(" not existing file.txt");

        assertFalse(f2.exists());

        parser.parseFilePath(f2);

        assertEquals(0, parser.getExtractedCategories().size());
    }

    @Test
    public void getExtractedCategories() {
        parser.parseFilePath(f);
        assertEquals(2, parser.getExtractedCategories().size());

        for (Category c : parser.getExtractedCategories()) {
            if (!"cat1".equals(c.getTitle()) && !"cat2".equals(c.getTitle())) {
                fail();
            }
        }
    }

    @Test
    public void clearFileName() {
        parser.parseFilePath(f);
        assertEquals("  asdf .txt", parser.clearFileName(f.getName()));
    }
}