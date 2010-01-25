package org.jtheque.movies.utils;

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

public class PreciseDuration {
	private final byte hours;
	private final byte minutes;
	private final byte seconds;
	private final short milliSeconds;

    private static final Pattern PATTERN = Pattern.compile(":");
    private static final Pattern PATTERN1 = Pattern.compile("\\.");

    public PreciseDuration(byte hours, byte minutes, byte seconds, byte milliSeconds){
		super();

		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		this.milliSeconds = milliSeconds;
	}

    public PreciseDuration(CharSequence duration) {
        super();

		//00 45 58.2

        String[] times = PATTERN.split(duration);

        hours = (byte) Integer.parseInt(times[0]);
        minutes = (byte) Integer.parseInt(times[1]);

        times = PATTERN1.split(times[2]);

        seconds = (byte) Integer.parseInt(times[0]);
        milliSeconds = (short) Integer.parseInt(times[1]);
    }

    public PreciseDuration(long time){
		super();

		hours = (byte) (time / (3600 * 1000));

		long rest = time - hours * 3600 * 1000;

		minutes = (byte) (rest / (60 * 1000));

		rest -= minutes * 60 * 1000;

		seconds = (byte) (rest / 1000);
		milliSeconds = (byte) (rest - seconds * 1000);
	}

	public long getTime(){
		return milliSeconds + seconds * 1000 + minutes * 60 * 1000 + hours * 3600 * 100;
	}

	@Override
	public String toString(){
		return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliSeconds);
	}

	@Override
	public boolean equals(Object o){
		if (this == o){
			return true;
		}

		if (o == null || getClass() != o.getClass()){
			return false;
		}

		PreciseDuration that = (PreciseDuration) o;

		return !(hours != that.hours || milliSeconds != that.milliSeconds ||
				minutes != that.minutes || seconds != that.seconds);
	}

	@Override
	public int hashCode(){
		int result = 17;

		result = 31 * result + (int) hours;
		result = 31 * result + (int) minutes;
		result = 31 * result + (int) seconds;
		result = 31 * result + (int) milliSeconds;

		return result;
	}
}