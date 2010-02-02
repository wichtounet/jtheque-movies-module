package org.jtheque.movies.utils;

import org.jtheque.utils.Constants;

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

/**
 * A precise duration. It's a duration in the format of hours:minutes:seconds.milliseconds.
 *
 * @author Baptiste Wicht
 */
public final class PreciseDuration {
    private final byte hours;
    private final byte minutes;
    private final byte seconds;
    private final short milliSeconds;

    private static final Pattern PATTERN = Pattern.compile(":");
    private static final Pattern PATTERN1 = Pattern.compile("\\.");

    private static final long MILLISECONDS = 1000L;
    private static final long SECONDS_PER_MINUTE = 60L;
    private static final long SECONDS_PER_HOUR = 3600L;

    /**
     * Construct a new PreciseDuration.
     *
     * @param duration The duration string.
     */
    public PreciseDuration(CharSequence duration) {
        super();

        String[] times = PATTERN.split(duration);

        hours = Byte.parseByte(times[0]);
        minutes = Byte.parseByte(times[1]);

        String[] second = PATTERN1.split(times[2]);

        seconds = Byte.parseByte(second[0]);
        milliSeconds = Short.parseShort(second[1]);
    }

    /**
     * Construct a new PreciseDuration.
     *
     * @param time The long time.
     */
    public PreciseDuration(long time) {
        super();

        hours = (byte) (time / (SECONDS_PER_HOUR * MILLISECONDS));

        long rest = time - hours * SECONDS_PER_HOUR * MILLISECONDS;

        minutes = (byte) (rest / (SECONDS_PER_MINUTE * MILLISECONDS));

        rest -= minutes * SECONDS_PER_MINUTE * MILLISECONDS;

        seconds = (byte) (rest / MILLISECONDS);
        milliSeconds = (short) (rest - seconds * MILLISECONDS);
    }

    /**
     * Return the long time of the duration.
     *
     * @return The long time of the duration.
     */
    public long getTime() {
        return milliSeconds +
                seconds * MILLISECONDS +
                minutes * SECONDS_PER_MINUTE * MILLISECONDS +
                hours * SECONDS_PER_HOUR * MILLISECONDS;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliSeconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PreciseDuration that = (PreciseDuration) o;

        return !(hours != that.hours || milliSeconds != that.milliSeconds || minutes != that.minutes || seconds != that.seconds);
    }

    @Override
    public int hashCode() {
        int result = Constants.HASH_CODE_START;

        result = Constants.HASH_CODE_START * result + (int) hours;
        result = Constants.HASH_CODE_START * result + (int) minutes;
        result = Constants.HASH_CODE_START * result + (int) seconds;
        result = Constants.HASH_CODE_START * result + (int) milliSeconds;

        return result;
	}
}