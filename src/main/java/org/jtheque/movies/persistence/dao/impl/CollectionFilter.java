package org.jtheque.movies.persistence.dao.impl;

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

import org.jtheque.movies.persistence.od.able.CollectionData;
import org.jtheque.core.managers.collection.Collection;
import org.jtheque.utils.collections.Filter;

/**
 * A collection filter to keep only the movies of the specific collection.
 *
 * @author Baptiste Wicht
 */
class CollectionFilter implements Filter<CollectionData> {
    private final Collection collection;

    /**
     * Construct a new CollectionFilter.
     *
     * @param collection The collection to filter with.
     */
    CollectionFilter(Collection collection) {
        super();

        this.collection = collection;
    }

    @Override
    public boolean accept(CollectionData data) {
        return data.isInCollection(collection);
    }
}
