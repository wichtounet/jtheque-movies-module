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

/*
 * Shell for Java ? adiGuba (http://adiguba.developpez.com)
 * adiGuba (mars 2007)
 *
 * Contact  : adiguba@redaction-developpez.com
 * Site web : http://adiguba.developpez.com/
 *
 * Ce logiciel est une librairie Java servant ? simplifier l'ex?cution
 * de programme natif ou de ligne de commande du shell depuis une
 * application Java, sans se soucier du syst?me h?te.
 *
 * Ce logiciel est r?gi par la licence CeCILL-C soumise au droit fran?ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL-C telle que diffus?e par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit? au code source et des droits de copie,
 * de modification et de redistribution accord?s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit?e.  Pour les m?mes raisons,
 * seule une responsabilit? restreinte p?se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc?dants successifs.
 *
 * A cet ?gard  l'attention de l'utilisateur est attir?e sur les risques
 * associ?s au chargement,  ? l'utilisation,  ? la modification et/ou au
 * d?veloppement et ? la reproduction du logiciel par l'utilisateur ?tant
 * donn? sa sp?cificit? de logiciel libre, qui peut le rendre complexe ?
 * manipuler et qui le r?serve donc ? des d?veloppeurs et des professionnels
 * avertis poss?dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit?s ? charger  et  tester  l'ad?quation  du
 * logiciel ? leurs besoins dans des conditions permettant d'assurer la
 * s?curit? de leurs syst?mes et ou de leurs donn?es et, plus g?n?ralement,
 * ? l'utiliser et l'exploiter dans les m?mes conditions de s?curit?.
 *
 * Le fait que vous puissiez acc?der ? cet en-t?te signifie que vous avez
 * pris connaissance de la licence CeCILL-C, et que vous en avez accept? les
 * termes.
 *
 */

/**
 * This class enable to launch an application and to redirect out and err flows to a StringBuilder.
 *
 * @author adiGuba
 * @author Baptiste Wicht
 */
public class SimpleApplicationConsumer {
	private final StringBuilder result = new StringBuilder(250);

	private final OutputStream out = new OutputStream() {
		@Override
		public void write(int b) throws IOException {
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
	};

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
	 * @return Le code de retour du process.
	 * @see Process#exitValue()
	 * @throws IOException Erreur d'E/S
	 */
	public int consume() throws IOException {
		final Process process = builder.start();

		try {
			process.getOutputStream().close();

			dump(new InputStreamReader(process.getErrorStream()), stderr);
			dump(new InputStreamReader(process.getInputStream()), stdout);

			try {
				return process.waitFor();
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
}