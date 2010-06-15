package org.jtheque.movies.persistence.od.able;

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
 * A category specification
 *
 * @author Baptiste Wicht
 */
public interface Category extends CollectionData {
    String NAME = "category.name";
    String PARENT = "category.parent";

    int NAME_LENGTH = 100;

    /**
     * Return the title of the category.
     *
     * @return The title of the category.
     */
    String getTitle();

    /**
     * Set the title of the category.
     *
     * @param title The title of the category.
     */
    void setTitle(String title);

    /**
     * Return the parent category.
     *
     * @return the parent Category or null if there is no parent.
     */
    Category getParent();

    /**
     * Set the parent category.
     *
     * @param parent The parent category.
     */
    void setParent(Category parent);

    /**
     * Set the temporary parent. This method is used when we cannot get the category referenced by the specified id.
     * This method must not be used once the cache of categories is loaded.
     *
     * @param parentId The id of the parent category.
     */
    void setTemporaryParent(int parentId);

    /**
     * Return the id of the temporary parent category.
     *
     * @return The id of the parent category.
     *
     * @see #setTemporaryParent(int)
     */
    int getTemporaryParent();
}
