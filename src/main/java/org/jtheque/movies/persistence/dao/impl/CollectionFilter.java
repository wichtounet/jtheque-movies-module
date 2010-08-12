package org.jtheque.movies.persistence.dao.impl;

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

import org.jtheque.collections.DataCollection;
import org.jtheque.movies.persistence.od.able.CollectionData;
import org.jtheque.utils.collections.Filter;

/**
 * A collection filter to keep only the movies of the specific collection.
 *
 * @author Baptiste Wicht
 */
class CollectionFilter<T extends CollectionData> implements Filter<T> {
    private final DataCollection collection;

    /**
     * Construct a new CollectionFilter.
     *
     * @param collection The collection to filter with.
     */
    CollectionFilter(DataCollection collection) {
        super();

        this.collection = collection;
    }

    @Override
    public boolean accept(T data) {
        return data.isInCollection(collection);
    }
}
