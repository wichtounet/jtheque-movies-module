package org.jtheque.movies.views.able;

import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.ui.able.IView;

/**
 * Created by IntelliJ IDEA. User: wichtounet Date: Jul 21, 2010 Time: 12:10:30 PM To change this template use File |
 * Settings | File Templates.
 */
public interface IEditMovieView extends IView {
    /**
     * Return the entered file path.
     *
     * @return The entered file path.
     */
    String getFilePath();

    /**
     * Set the resolution.
     *
     * @param resolution The resolution to set.
     */
    void setResolution(Resolution resolution);

    /**
     * Set the duration.
     *
     * @param duration The duration to set.
     */
    void setDuration(PreciseDuration duration);
}
