package org.jtheque.movies.services.impl;

import org.jtheque.core.utils.CoreUtils;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.CharBuffer;

/**
 * This class enable to launch an application and to redirect out and err flows to a StringBuilder.
 *
 * @author adiGuba
 * @author Baptiste Wicht
 */
public final class SimpleApplicationConsumer {
	private final StringBuilder result = new StringBuilder(250);

	private final OutputStream out = new StringBuilderOutputStream();

	private final Appendable stdout = new PrintStream(out, true);
	private final Appendable stderr = new PrintStream(out, true);

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
	 * Consume tous les flux du process en associant les diff?rents flux. Cette
	 * m?thode est bloquante tant que le process n'est pas termin?.
	 *  
	 * @see Process#exitValue()
	 * @throws IOException Erreur d'E/S
	 */
	public void consume() throws IOException {
		final Process process = builder.start();

		try {
			process.getOutputStream().close();

			dump(new InputStreamReader(process.getErrorStream()), stderr);
			dump(new InputStreamReader(process.getInputStream()), stdout);

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
	 * Tente de fermer l'objet en param?tre.
	 *
	 * @param c
	 *            L'objet ? fermer.
	 */
	private void tryToClose(Object c) {
		try {
			((Closeable) c).close();
		} catch (IOException e) {
			CoreUtils.getLogger(getClass()).error(e);
		}
	}

	/**
	 * Copie des flux de <code>in</code> vers <code>out</code>.
	 *
	 * @param in
	 *            Flux depuis lequel les donn?es seront lues
	 * @param out
	 *            Flux vers lequel les donn?es seront ?crites
	 * @throws IOException
	 *             Erreur E/S
	 */
	private void dump(Readable in, Appendable out) throws IOException {
		try {
			try {
				Flushable flushable = (Flushable) out;

				Thread current = Thread.currentThread();
				CharBuffer cb = CharBuffer.allocate(BUF_SIZE);
				int len;

				cb.clear();
				while ((len = in.read(cb)) > 0 && !current.isInterrupted()) {
					cb.position(0).limit(len);
					out.append(cb);
					cb.clear();
					flushable.flush();

					if(current.isInterrupted()){
						break;
					}
				}
			} finally {
				tryToClose(in);
			}
		} finally {
			tryToClose(out);
		}
	}

	public String getResult(){
		return result.toString();
	}

	private final class StringBuilderOutputStream extends OutputStream {
		@Override
		public void write(int b) throws IOException{
			result.append(String.valueOf((char) b));
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException{
			result.append(new String(b, off, len));
		}

		@Override
		public void write(byte[] b) throws IOException{
			write(b, 0, b.length);
		}
	}
}