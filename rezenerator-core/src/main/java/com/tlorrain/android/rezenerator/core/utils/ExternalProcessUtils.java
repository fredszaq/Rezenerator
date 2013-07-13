package com.tlorrain.android.rezenerator.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.tlorrain.android.rezenerator.core.log.Logger;

public class ExternalProcessUtils {
	private static class AsyncStreamPrinter extends Thread {

		private final InputStream inputStream;
		private Printer printer;

		AsyncStreamPrinter(InputStream inputStream, Printer printer) {
			this.inputStream = inputStream;
			this.printer = printer;
		}

		@Override
		public void run() {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String line = "";
			try {
				while ((line = br.readLine()) != null) {
					printer.print(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static interface Printer {
		void print(String toPrint);
	}

	private static class ErrorPrinter implements Printer {
		private Logger logger;

		public ErrorPrinter(Logger logger) {
			this.logger = logger;
		}

		@Override
		public void print(String toPrint) {
			logger.error(toPrint);

		}
	}

	private static class StandardPrinter implements Printer {
		private Logger logger;

		public StandardPrinter(Logger logger) {
			this.logger = logger;
		}

		@Override
		public void print(String toPrint) {
			logger.info(toPrint);

		}
	}

	public static int executeProcess(ProcessBuilder pb, Logger logger) {
		try {
			Process process = pb.start();
			AsyncStreamPrinter stardardOutputThread = new AsyncStreamPrinter(process.getInputStream(), new StandardPrinter(logger));
			stardardOutputThread.start();
			AsyncStreamPrinter errorOutputThread = new AsyncStreamPrinter(process.getErrorStream(), new ErrorPrinter(logger));
			errorOutputThread.start();
			stardardOutputThread.join();
			errorOutputThread.join();
			return process.waitFor();
		} catch (Exception e) {
			throw new RuntimeException("error while excecuting " + pb.command(), e);
		}
	}
}
