package com.tlorrain.android.rezenerator.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExternalProcessUtils {
	private static class AsyncStreamPrinter extends Thread {

		private final InputStream inputStream;

		AsyncStreamPrinter(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public void run() {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String line = "";
			try {
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int executeProcess(ProcessBuilder pb) {
		try {
			Process process = pb.start();
			AsyncStreamPrinter stardardOutputThread = new AsyncStreamPrinter(process.getInputStream());
			stardardOutputThread.start();
			AsyncStreamPrinter errorOutputThread = new AsyncStreamPrinter(process.getErrorStream());
			errorOutputThread.start();
			stardardOutputThread.join();
			errorOutputThread.join();
			return process.waitFor();
		} catch (Exception e) {
			throw new RuntimeException("error while excecuting " + pb.command(), e);
		}
	}

}
