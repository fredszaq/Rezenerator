package com.tlorrain.android.rezenerator.core.log;

public interface Logger {
	void info(String info);

	void verbose(String debug);

	void verbose(Exception exception);

	void error(String error);
}
