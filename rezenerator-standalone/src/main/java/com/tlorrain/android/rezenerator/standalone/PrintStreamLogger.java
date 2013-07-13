package com.tlorrain.android.rezenerator.standalone;

import java.io.PrintStream;

import com.tlorrain.android.rezenerator.core.log.Logger;

public class PrintStreamLogger implements Logger {

	PrintStream standardStream;
	PrintStream errorStream;

	public PrintStreamLogger(PrintStream standardStream, PrintStream errorStream) {
		this.standardStream = standardStream;
		this.errorStream = errorStream;
	}

	@Override
	public void info(String info) {
		standardStream.println(info);
	}

	@Override
	public void verbose(String debug) {
		standardStream.println(debug);
	}

	@Override
	public void error(String error) {
		errorStream.println(error);
	}

	@Override
	public void verbose(Exception exception) {
		exception.printStackTrace(standardStream);
	}

}
