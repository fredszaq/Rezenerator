package com.tlorrain.android.rezenerator.core.log;

public class NoopLogger implements Logger {

	@Override
	public void info(String info) {
	}

	@Override
	public void verbose(String debug) {
	}

	@Override
	public void error(String error) {
	}

	@Override
	public void verbose(Exception exception) {
	}

}
