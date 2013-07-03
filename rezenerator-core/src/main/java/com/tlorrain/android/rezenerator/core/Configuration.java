package com.tlorrain.android.rezenerator.core;

import java.io.File;

public class Configuration {
	private File inDir;
	private File baseOutDir;
	private boolean verbose = true;
	private boolean forceUpdate = false;

	public File getInDir() {
		return inDir;
	}

	public Configuration setInDir(File inDir) {
		this.inDir = inDir;
		return this;
	}

	public File getBaseOutDir() {
		return baseOutDir;
	}

	public Configuration setBaseOutDir(File baseOutDir) {
		this.baseOutDir = baseOutDir;
		return this;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public Configuration setVerbose(boolean verbose) {
		this.verbose = verbose;
		return this;
	}

	public boolean isForceUpdate() {
		return forceUpdate;
	}

	public Configuration setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
		return this;
	}

}
