package org.jtheque.movies.persistence;

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

import org.jtheque.collections.able.IDaoCollections;
import org.jtheque.movies.persistence.dao.able.IDaoCategories;
import org.jtheque.movies.persistence.dao.able.IDaoMovies;
import org.jtheque.schemas.able.DefaultSchema;
import org.jtheque.utils.bean.Version;

/**
 * The database schema for the Movies Module.
 *
 * @author Baptiste Wicht
 */
public final class MoviesSchema extends DefaultSchema {
    /**
     * Construct a new MoviesSchema.
     */
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
        alterTable(IDaoCategories.TABLE, "ADD FOREIGN KEY (THE_PARENT_FK) REFERENCES " + IDaoCategories.TABLE + " (ID) ON UPDATE SET NULL");
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
        alterTable(IDaoMovies.MOVIES_CATEGORIES_TABLE, "ADD FOREIGN KEY (THE_MOVIE_FK) REFERENCES  " + IDaoMovies.TABLE + "(ID) ON UPDATE SET NULL");
        alterTable(IDaoMovies.MOVIES_CATEGORIES_TABLE, "ADD FOREIGN KEY (THE_CATEGORY_FK) REFERENCES " + IDaoCategories.TABLE + " (ID) ON UPDATE SET NULL");
        alterTable(IDaoCategories.TABLE, "ADD FOREIGN KEY (THE_COLLECTION_FK) REFERENCES  " + IDaoCollections.TABLE + " (ID) ON UPDATE SET NULL");
        alterTable(IDaoMovies.TABLE, "ADD FOREIGN KEY (THE_COLLECTION_FK) REFERENCES " + IDaoCollections.TABLE + " (ID) ON UPDATE SET NULL");
        alterTable(IDaoCategories.TABLE, "ADD FOREIGN KEY (THE_PARENT_FK) REFERENCES " + IDaoCategories.TABLE + " (ID) ON UPDATE SET NULL");
    }
}