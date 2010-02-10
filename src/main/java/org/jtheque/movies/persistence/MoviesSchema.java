package org.jtheque.movies.persistence;

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

import org.jtheque.core.managers.schema.AbstractSchema;
import org.jtheque.core.managers.schema.HSQLImporter;
import org.jtheque.core.managers.schema.Insert;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.primary.dao.able.IDaoCollections;
import org.jtheque.utils.bean.Version;

/**
 * The database schema for the Movies Module.
 *e
 * @author Baptiste Wicht
 */
public final class MoviesSchema extends AbstractSchema {
    private static final String ALTER_TABLE = "ALTER TABLE ";
    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String CREATE_TABLE = "CREATE TABLE ";

    @Override
    public Version getVersion() {
        return new Version("1.2");
    }

    @Override
    public String getId() {
        return "Movies-Schema";
    }

    @Override
    public String[] getDependencies() {
        return new String[]{"PrimaryUtils-Schema"};
    }

    @Override
    public void install() {
        createDataTables();
        createReferentialIntegrityConstraints();
    }

    @Override
    public void update(Version from) {
        String fromVersion = from.getVersion();

        if ("1.0".equals(fromVersion) || "1.1".equals(fromVersion)) {
            if ("1.0".equals(fromVersion)) {
                createReferentialIntegrityConstraints();
            }

            correctUnicityConstraints();
            addInformationColumns();
        }
    }

    /**
     * Correct the unicity constraints.
     */
    private void correctUnicityConstraints() {
        update(ALTER_TABLE + IDaoMovies.TABLE + " DROP CONSTRAINT CONSTRAINT_INDEX_3F");
        update(ALTER_TABLE + IDaoMovies.TABLE + " ADD CONSTRAINT UNIQUE_FILE UNIQUE(FILE)");
    }

    /**
     * Add the informations column.
     */
    private void addInformationColumns() {
        update(ALTER_TABLE + IDaoMovies.TABLE + " ADD DURATION BIGINT");
        update(ALTER_TABLE + IDaoMovies.TABLE + " ADD RESOLUTION VARCHAR(11)");
        update(ALTER_TABLE + IDaoMovies.TABLE + " ADD IMAGE VARCHAR(150)");
        update(ALTER_TABLE + IDaoCategories.TABLE + " ADD THE_PARENT_FK INT");
        update(ALTER_TABLE + IDaoCategories.TABLE + " ADD FOREIGN KEY (THE_PARENT_FK) REFERENCES  " + IDaoCategories.TABLE + "  (ID) ON UPDATE SET NULL");
    }

    /**
     * Create the data tables.
     */
    private void createDataTables() {
        update(CREATE_TABLE + IDaoMovies.MOVIES_CATEGORIES_TABLE + " (THE_MOVIE_FK INT NOT NULL, THE_CATEGORY_FK INT NOT NULL)");
        update(CREATE_TABLE + IDaoCategories.TABLE + " (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(100) NOT NULL UNIQUE, THE_PARENT_FK INT, THE_COLLECTION_FK INT NOT NULL)");
        update(CREATE_TABLE + IDaoMovies.TABLE + " (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(100) NOT NULL, NOTE INT NULL, FILE VARCHAR(200) NOT NULL UNIQUE, DURATION BIGINT, RESOLUTION VARCHAR(11), IMAGE VARCHAR(150), THE_COLLECTION_FK INT NOT NULL)");

        update("CREATE INDEX MOVIES_IDX ON " + IDaoMovies.TABLE + "(ID)");
        update("CREATE INDEX MOVIE_CATEGORIES_IDX ON " + IDaoCategories.TABLE + "(ID)");
    }

    /**
     * Create the constraints to maintain a referential integrity in the database.
     */
    private void createReferentialIntegrityConstraints() {
        update(ALTER_TABLE + IDaoMovies.MOVIES_CATEGORIES_TABLE + " ADD FOREIGN KEY (THE_MOVIE_FK) REFERENCES  " + IDaoMovies.TABLE + "  (ID) ON UPDATE SET NULL");
        update(ALTER_TABLE + IDaoMovies.MOVIES_CATEGORIES_TABLE + " ADD FOREIGN KEY (THE_CATEGORY_FK) REFERENCES  " + IDaoCategories.TABLE + "  (ID) ON UPDATE SET NULL");
        update(ALTER_TABLE + IDaoCategories.TABLE + " ADD FOREIGN KEY (THE_COLLECTION_FK) REFERENCES  " + IDaoCollections.TABLE + "  (ID) ON UPDATE SET NULL");
        update(ALTER_TABLE + IDaoMovies.TABLE + " ADD FOREIGN KEY (THE_COLLECTION_FK) REFERENCES  " + IDaoCollections.TABLE + "  (ID) ON UPDATE SET NULL");
        update(ALTER_TABLE + IDaoCategories.TABLE + " ADD FOREIGN KEY (THE_PARENT_FK) REFERENCES  " + IDaoCategories.TABLE + "  (ID) ON UPDATE SET NULL");
    }

    @Override
    public void importDataFromHSQL(Iterable<Insert> inserts) {
        HSQLImporter importer = new HSQLImporter();

        importer.match("MOVIE_CATEGORY", INSERT_INTO + IDaoMovies.MOVIES_CATEGORIES_TABLE + " (THE_MOVIE_FK, THE_CATEGORY_FK) VALUES(?, ?)", 0, 1);
        importer.match("OD_CATEGORY", INSERT_INTO + IDaoCategories.TABLE + " (ID, TITLE, THE_COLLECTION_FK) VALUES(?, ?, ?)", 0, 2, 3);
        importer.match("OD_MOVIE", INSERT_INTO + IDaoMovies.TABLE + " (ID, TITLE, NOTE, FILE, THE_COLLECTION_FK) VALUES(?, ?, ?, ?, ?)", 0, 4, 3, 2, 5);
        importer.match("OD_MOVIE_COLLECTION", INSERT_INTO + IDaoCollections.TABLE + " (ID, TITLE, PROTECTED, PASSWORD, IMPL) VALUES(?,?,?,?,?)", "Movies", 0, 4, 3, 2);

        importer.importInserts(inserts);
    }
}