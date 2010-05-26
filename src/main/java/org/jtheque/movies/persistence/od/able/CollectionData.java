package org.jtheque.movies.persistence.od.able;

import org.jtheque.collections.able.Collection;
import org.jtheque.primary.able.od.Data;

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