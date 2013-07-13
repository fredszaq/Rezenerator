package com.tlorrain.android.rezenerator.core;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.tlorrain.android.rezenerator.core.log.Logger;
import com.tlorrain.android.rezenerator.core.log.NoopLogger;

public class Configuration {
	private File inDir;
	private File baseOutDir;
	private boolean verbose = true;
	private boolean forceUpdate = false;
	private List<String> scannedPackages = new LinkedList<String>();
	private Logger logger = new NoopLogger();

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

	public List<String> getScannedPackages() {
		return scannedPackages;
	}

	public Configuration addScannedPackage(String scannedPackage) {
		scannedPackages.add(scannedPackage);
		return this;
	}

	public Logger getLogger() {
		return logger;
	}

	public Configuration setLogger(Logger logger) {
		this.logger = logger;
		return this;
	}

}
