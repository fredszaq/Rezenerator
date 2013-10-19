package com.tlorrain.android.rezenerator.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RunResult {

	private final Map<File, Exception> errors = new HashMap<File, Exception>();

	private boolean successful;

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(final boolean successful) {
		this.successful = successful;
	}

	public Map<File, Exception> getErrors() {
		return errors;
	}

	public void addError(final File on, final Exception exception) {
		errors.put(on, exception);
	}

}
