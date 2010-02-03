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

import org.jtheque.utils.io.FileUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * This class enable to launch an application and to redirect resultStream and err flows to a StringBuilder.
 *
 * @author adiGuba
 * @author Baptiste Wicht
 */
public final class SimpleApplicationConsumer {
    private final StringBuilder result = new StringBuilder(250);

    private final OutputStream resultStream = new StringBuilderOutputStream();

    private final PrintStream out = new PrintStream(resultStream, true);
    private final PrintStream err = new PrintStream(resultStream, true);

    private static final int BUF_SIZE = 8192;

    private final ProcessBuilder builder;

    /**
     * Construct a new SimpleApplicationConsumer to launch a simple application with some arguments.
     *
     * @param args The application followed by the arguments to pass to the application.
     */
    public SimpleApplicationConsumer(String... args) {
        super();

        builder = new ProcessBuilder(args);
    }

    /**
     * Consume all the streams of the process. All the streams are redirected to
     * a simple StringBuilder.
     *
     * @throws IOException I/O error
     */
    public void consume() throws IOException {
        final Process process = builder.start();

        try {
            process.getOutputStream().close();

            dump(new InputStreamReader(process.getErrorStream()), err);
            dump(new InputStreamReader(process.getInputStream()), out);

            try {
                process.waitFor();
            } catch (InterruptedException e) {
                IOException ioe = new InterruptedIOException();
                ioe.initCause(e);
                throw ioe;
            }
        } finally {
            process.destroy();
        }
    }

    /**
     * Dump the in stream into the resultStream stream.
     *
     * @param in  input stream.
     * @param out output stream.
     * @throws IOException I/O error
     */
    private static void dump(Reader in, PrintStream out) throws IOException {
        try {
            try {
                Thread current = Thread.currentThread();
                CharBuffer cb = CharBuffer.allocate(BUF_SIZE);


                cb.clear();

                int len = in.read(cb);

                while (len > 0 && !current.isInterrupted()) {
                    cb.position(0).limit(len);
                    out.append(cb);
                    cb.clear();
                    out.flush();

                    if (current.isInterrupted()) {
                        break;
                    }

                    len = in.read(cb);
                }
            } finally {
                FileUtils.close(in);
            }
        } finally {
            FileUtils.close(out);
        }
    }

    /**
     * Return the result of the consumed process.
     *
     * @return The String result of the consumed process.
     */
    public String getResult() {
        return result.toString();
    }

    /**
     * An output stream to write to a string builder.
     *
     * @author Baptiste Wicht
     */
    private final class StringBuilderOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            result.append(String.valueOf((char) b));
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            result.append(new String(b, off, len));
        }

        @Override
        public void write(byte[] b) throws IOException{
			write(b, 0, b.length);
		}
	}
}