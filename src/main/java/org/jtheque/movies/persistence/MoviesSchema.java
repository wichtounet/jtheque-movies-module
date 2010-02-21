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

import org.jtheque.core.managers.schema.DefaultSchema;
import org.jtheque.core.managers.schema.HSQLImporter;
import org.jtheque.core.managers.schema.Insert;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.core.managers.collection.IDaoCollections;
import org.jtheque.utils.bean.Version;

/**
 * The database schema for the Movies Module.
 *
 * @author Baptiste Wicht
 */
public final class MoviesSchema extends DefaultSchema {
    public MoviesSchema() {
        super(new Version("1.2"), "Movies-Schema", "PrimaryUtils-Schema");
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
        alterTable(IDaoMovies.TABLE, "DROP CONSTRAINT CONSTRAINT_INDEX_3F");
        alterTable(IDaoMovies.TABLE, "ADD CONSTRAINT UNIQUE_FILE UNIQUE(FILE)");
    }

    /**
     * Add the informations column.
     */
    private void addInformationColumns() {
        alterTable(IDaoMovies.TABLE, "ADD DURATION BIGINT");
        alterTable(IDaoMovies.TABLE, "ADD RESOLUTION VARCHAR(11)");
        alterTable(IDaoMovies.TABLE, "ADD IMAGE VARCHAR(150)");
        alterTable(IDaoCategories.TABLE, "ADD THE_PARENT_FK INT");
        alterTable(IDaoCategories.TABLE, "ADD FOREIGN KEY (THE_PARENT_FK) REFERENCES ? (ID) ON UPDATE SET NULL", IDaoCategories.TABLE);
    }

    /**
     * Create the data tables.
     */
    private void createDataTables() {
        createTable(IDaoMovies.MOVIES_CATEGORIES_TABLE, "THE_MOVIE_FK INT NOT NULL, THE_CATEGORY_FK INT NOT NULL");
        createTable(IDaoCategories.TABLE, "ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(100) NOT NULL UNIQUE, THE_PARENT_FK INT, THE_COLLECTION_FK INT NOT NULL");
        createTable(IDaoMovies.TABLE, "ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(100) NOT NULL, NOTE INT NULL, FILE VARCHAR(200) NOT NULL UNIQUE, DURATION BIGINT, RESOLUTION VARCHAR(11), IMAGE VARCHAR(150), THE_COLLECTION_FK INT NOT NULL");

        updateTable(IDaoMovies.TABLE, "CREATE INDEX MOVIES_IDX ON {} (ID)");
        updateTable(IDaoCategories.TABLE, "CREATE INDEX MOVIE_CATEGORIES_IDX ON {} (ID)");
    }

    /**
     * Create the constraints to maintain a referential integrity in the database.
     */
    private void createReferentialIntegrityConstraints() {
        alterTable(IDaoMovies.MOVIES_CATEGORIES_TABLE, "ADD FOREIGN KEY (THE_MOVIE_FK) REFERENCES  ? (ID) ON UPDATE SET NULL", IDaoMovies.TABLE);
        alterTable(IDaoMovies.MOVIES_CATEGORIES_TABLE, "ADD FOREIGN KEY (THE_CATEGORY_FK) REFERENCES ? (ID) ON UPDATE SET NULL", IDaoCategories.TABLE);
        alterTable(IDaoCategories.TABLE, "ADD FOREIGN KEY (THE_COLLECTION_FK) REFERENCES  ? (ID) ON UPDATE SET NULL", IDaoCollections.TABLE);
        alterTable(IDaoMovies.TABLE, "ADD FOREIGN KEY (THE_COLLECTION_FK) REFERENCES ? (ID) ON UPDATE SET NULL", IDaoCollections.TABLE);
        alterTable(IDaoCategories.TABLE, "ADD FOREIGN KEY (THE_PARENT_FK) REFERENCES ? (ID) ON UPDATE SET NULL", IDaoCategories.TABLE);
    }

    @Override
    public void importDataFromHSQL(Iterable<Insert> inserts) {
        HSQLImporter importer = new HSQLImporter();

        importer.match("MOVIE_CATEGORY", insert(IDaoMovies.MOVIES_CATEGORIES_TABLE, "(THE_MOVIE_FK, THE_CATEGORY_FK) VALUES(?, ?)"), 0, 1);
        importer.match("OD_CATEGORY", insert(IDaoCategories.TABLE, "(ID, TITLE, THE_COLLECTION_FK) VALUES(?, ?, ?)"), 0, 2, 3);
        importer.match("OD_MOVIE", insert(IDaoMovies.TABLE, "(ID, TITLE, NOTE, FILE, THE_COLLECTION_FK) VALUES(?, ?, ?, ?, ?)"), 0, 4, 3, 2, 5);
        importer.match("OD_MOVIE_COLLECTION", insert(IDaoCollections.TABLE, "(ID, TITLE, PROTECTED, PASSWORD, IMPL) VALUES(?,?,?,?,?)"), "Movies", 0, 4, 3, 2);

        importer.importInserts(inserts);
    }
}