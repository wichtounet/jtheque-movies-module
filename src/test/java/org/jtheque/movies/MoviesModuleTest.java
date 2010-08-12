package org.jtheque.movies;

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

import org.jtheque.core.Core;
import org.jtheque.core.application.Application;
import org.jtheque.core.application.ApplicationProperties;
import org.jtheque.core.utils.ImageType;
import org.jtheque.utils.bean.BeanUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class MoviesModuleTest {
    @Resource
    private IMoviesModule moviesModule;

    @Resource
    private Core core;

    @Test
    public void testGetThumbnailFolderPath() throws Exception {
        BeanUtils.set(core, "application", new EmptyApplication());

        assertNotNull(moviesModule.getThumbnailFolderPath());

        String thumbnailFolder = moviesModule.getThumbnailFolderPath();

        assertTrue(new File(thumbnailFolder).exists());
        assertTrue(FileUtils.isFileInDirectory(new File(thumbnailFolder), core.getFolders().getApplicationFolder()));
    }

    public static class EmptyApplication implements Application {
        @Override
        public String getLogo() {
            return null;
        }

        @Override
        public ImageType getLogoType() {
            return null;
        }

        @Override
        public String getWindowIcon() {
            return null;
        }

        @Override
        public String getLicenseFilePath() {
            return null;
        }

        @Override
        public Version getVersion() {
            return null;
        }

        @Override
        public String getFolderPath() {
            return System.getenv("JTHEQUE_TESTS");
        }

        @Override
        public boolean isDisplayLicense() {
            return false;
        }

        @Override
        public String getRepository() {
            return null;
        }

        @Override
        public String getMessageFileURL() {
            return null;
        }

        @Override
        public ApplicationProperties getI18nProperties() {
            return null;
        }

        @Override
        public String[] getSupportedLanguages() {
            return null;
        }

        @Override
        public String getProperty(String key) {
            return null;
        }
    }
}