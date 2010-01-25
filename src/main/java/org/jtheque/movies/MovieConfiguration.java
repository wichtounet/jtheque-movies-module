package org.jtheque.movies;

import org.jtheque.core.managers.state.AbstractState;
import org.jtheque.utils.StringUtils;

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
 * A movies configuration implementation.
 *
 * @author Baptiste Wicht
 */
public final class MovieConfiguration extends AbstractState implements IMovieConfiguration {
	private static final String OPENING_SYSTEM = "opening";
	private static final String FFMPEG_LOCATION = "ffmpeg";

	@Override
    public Opening getOpeningSystem(){
        String opening = getProperty(OPENING_SYSTEM, "system");

        if (Opening.SYSTEM.getValue().equals(opening)){
            return Opening.SYSTEM;
        } else if (Opening.VLC.getValue().equals(opening)){
            return Opening.VLC;
        } else if (Opening.WMP.getValue().equals(opening)){
            return Opening.WMP;
        } else {
            return Opening.SYSTEM;
        }
    }

    @Override
    public void setOpeningSystem(Opening opening){
        setProperty(OPENING_SYSTEM, opening.getValue());
    }

	@Override
	public boolean isFFmpegInstalled(){
		return StringUtils.isNotEmpty(getFFmpegLocation());
	}
	
	@Override
	public String getFFmpegLocation(){
		return getProperty(FFMPEG_LOCATION, "");
	}

	@Override
	public void setFFmpegLocation(String location){
		setProperty(FFMPEG_LOCATION, location);
	}
}