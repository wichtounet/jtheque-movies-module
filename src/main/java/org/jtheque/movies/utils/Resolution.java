package org.jtheque.movies.utils;

import org.jtheque.utils.Constants;
import org.jtheque.utils.StringUtils;

import java.util.regex.Pattern;

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
 * A resolution for movie. The resolution is of format "widthxheight".
 *
 * @author Baptiste Wicht
 */
public final class Resolution {
    private final short width;
    private final short height;

    private static final Pattern RESOLUTION_PATTERN = Pattern.compile("x");

    /**
     * Construct a new Resolution.
     *
     * @param resolution The string representation of the resolution.
     */
    public Resolution(String resolution) {
        super();

        if (StringUtils.isEmpty(resolution) || !resolution.contains("x")) {
            throw new IllegalArgumentException("Resolution must be of form \"widthxheigh\"");
        }

        String[] sizes = RESOLUTION_PATTERN.split(resolution);

        width = Short.parseShort(sizes[0]);
        height = Short.parseShort(sizes[1]);
    }

    @Override
    public String toString() {
        return String.format("%04dx%04d", width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resolution that = (Resolution) o;

        return !(height != that.height || width != that.width);
    }

    @Override
    public int hashCode() {
        return Constants.HASH_CODE_START + Constants.HASH_CODE_PRIME * width + height;
	}
}