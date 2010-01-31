package org.jtheque.movies;

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

import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.core.application.Application;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.io.File;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/org/jtheque/core/spring/core-test-beans.xml",
        "/org/jtheque/movies/movies-test-beans.xml",
        "/org/jtheque/primary/spring/primary-test-beans.xml"})
public class MoviesModuleTest {
    @Resource
    private IMoviesModule moviesModule;

    @Test
    public void testGetThumbnailFolderPath() throws Exception {
        Field f = Core.class.getDeclaredField("application");

        f.setAccessible(true);

        f.set(Core.getInstance(), new EmptyApplication());

        assertNotNull(moviesModule.getThumbnailFolderPath());

        String thumbnailFolder = moviesModule.getThumbnailFolderPath();
        
        assertTrue(new File(thumbnailFolder).exists());
        assertTrue(FileUtils.isFileInDirectory(new File(thumbnailFolder),
                Core.getInstance().getFolders().getApplicationFolder()));
    }

    private static class EmptyApplication implements Application {
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
        public ImageType getWindowIconType() {
            return null;
        }

        @Override
        public String getLicenceFilePath() {
            return null;
        }

        @Override
        public Version getVersion() {
            return null;
        }

        @Override
        public String getFolderPath() {
            return SystemProperty.USER_DIR.get();
        }

        @Override
        public boolean isDisplayLicence() {
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
        public String getName() {
            return null;
        }

        @Override
        public String getAuthor() {
            return null;
        }

        @Override
        public String getEmail() {
            return null;
        }

        @Override
        public String getSite() {
            return null;
        }

        @Override
        public String getCopyright() {
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