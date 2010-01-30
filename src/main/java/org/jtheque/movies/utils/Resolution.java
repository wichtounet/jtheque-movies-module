package org.jtheque.movies.utils;

import org.jtheque.utils.StringUtils;

import java.util.regex.Pattern;

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

public final class Resolution {
	private final short width;
	private final short height;

	private static final Pattern RESOLUTION_PATTERN = Pattern.compile("x");

	public Resolution(String resolution){
		super();

		if(StringUtils.isEmpty(resolution) || !resolution.contains("x")){
			throw new IllegalArgumentException("Resolution must be of form \"widthxheigh\"");
		}

		String[] sizes = RESOLUTION_PATTERN.split(resolution);

		width = Short.parseShort(sizes[0]);
		height = Short.parseShort(sizes[1]);
	}

	@Override
	public String toString(){
		return String.format("%04dx%04d", width, height);
	}

	@Override
	public boolean equals(Object o){
		if (this == o){
			return true;
		}

		if (o == null || getClass() != o.getClass()){
			return false;
		}

		Resolution that = (Resolution) o;

		return !(height != that.height || width != that.width);
	}

	@Override
	public int hashCode(){
		return 17 + 31 * width + height;
	}
}