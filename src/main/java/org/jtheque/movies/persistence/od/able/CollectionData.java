package org.jtheque.movies.persistence.od.able;

import org.jtheque.primary.od.able.Collection;
import org.jtheque.primary.od.able.Data;

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

public interface CollectionData extends Data {
    /**
     * Return the collection.
     *
     * @return The collection.
     */
    Collection getTheCollection();

    /**
     * Set the collection of the movie.
     *
     * @param theCollection The collection of the movie.
     */
    void setTheCollection(Collection theCollection);

    /**
     * Test if the movie is in the specified collection.
     *
     * @param collection The collection to test.
     * @return true if the movie is in the specified collection else false.
     */
    boolean isInCollection(Collection collection);
}