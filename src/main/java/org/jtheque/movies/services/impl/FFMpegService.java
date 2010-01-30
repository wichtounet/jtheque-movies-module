package org.jtheque.movies.services.impl;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.movies.IMovieConfiguration;
import org.jtheque.movies.IMoviesModule;
import org.jtheque.movies.services.able.IFFMpegService;
import org.jtheque.movies.utils.PreciseDuration;
import org.jtheque.movies.utils.Resolution;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A files service implementation.
 *
 * @author Baptiste Wicht
 */
public final class FFMpegService implements IFFMpegService {
    private static final Pattern PATTERN = Pattern.compile(", ");
	private static final Random RANDOM = new Random();

    @Override
    public Resolution getResolution(File f){
        if(ffmpegIsInstalled()){
            Scanner scanner = getInformations(f);

            if(scanner != null){
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine().trim();

                    if(line.startsWith("Stream #0.0: Video:")){
                        String resolution = PATTERN.split(line)[2].trim();

						if(resolution.contains(" ")){
							resolution = resolution.substring(0, resolution.indexOf(' '));
						}

                        return new Resolution(resolution);
                    }
                }
            }
        }

        return null;
    }

    @Override
    public PreciseDuration getDuration(File f){
        if(ffmpegIsInstalled()){
            Scanner scanner = getInformations(f);

            if(scanner != null){
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine().trim();

                    if(line.startsWith("Duration:")){
                        String duration = line.substring(10, line.indexOf(',')) + "00";

                        return new PreciseDuration(duration);
                    }
                }
            }
        }

        return null;
    }

	@Override
	public BufferedImage generateRandomPreviewImage(File f){
        return generatePreviewImage(f, getRandomTime());
	}

	@Override
	public BufferedImage generatePreviewImage(File f, String time){
		if(ffmpegIsInstalled()){
			String fileName = getModule().getThumbnailFolderPath() + "temp.jpg";

			SimpleApplicationConsumer p = new SimpleApplicationConsumer(getModule().getConfig().getFFmpegLocation(),
					"-i", 	f.getAbsolutePath(),
					"-f", 	"mjpeg",
					"-t", 	"0.001",
					"-ss", 	time,
					"-y", 	fileName);

			try {
				p.consume();
			} catch (IOException e) {
				CoreUtils.getLogger(getClass()).error(e);
			}

			BufferedImage image = ImageUtils.openCompatibleImage(
				Managers.getManager(IResourceManager.class).getResourceAsStream("file:" + fileName));

			return ImageUtils.createThumbnail(image, 200);
		}

		return null;
	}

	@Override
	public BufferedImage generateImageFromUserInput(File file){
		BufferedImage image = ImageUtils.openCompatibleImage(
				Managers.getManager(IResourceManager.class).getResourceAsStream("file:" + file.getAbsolutePath()));

		return ImageUtils.createThumbnail(image, 200);
	}

	private static boolean ffmpegIsInstalled() {
        IMovieConfiguration config = getModule().getConfig();

        boolean notInstalled = StringUtils.isEmpty(config.getFFmpegLocation()) || !new File(config.getFFmpegLocation()).exists();

        if(notInstalled){
            Managers.getManager(IViewManager.class).displayError(new InternationalizedError("movie.errors.ffmpeg"));
        }

        return !notInstalled;
    }

    private Scanner getInformations(File f) {
		IMovieConfiguration config = getModule().getConfig();

        SimpleApplicationConsumer p = new SimpleApplicationConsumer(config.getFFmpegLocation(), "-i", f.getAbsolutePath());

        try {
            p.consume();

            return new Scanner(p.getResult());
        } catch (IOException e) {
            CoreUtils.getLogger(getClass()).error(e);
        }

        return null;
    }

    private static IMoviesModule getModule(){
        return CoreUtils.getBean("moviesModule");
    }

	private static String getRandomTime(){
		return String.valueOf(RANDOM.nextInt(50) + 1);
	}
}