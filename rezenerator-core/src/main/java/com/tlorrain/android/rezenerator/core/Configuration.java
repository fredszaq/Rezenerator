package com.tlorrain.android.rezenerator.core;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.tlorrain.android.rezenerator.core.log.Logger;
import com.tlorrain.android.rezenerator.core.log.NoopLogger;

public class Configuration {

	/**
	 * The directory containing the image files to process
	 */
	private File inDir;

	/**
	 * The base directory where processed files should be output (eg the
	 * directory containing the mdpi, hdpi ... folders)
	 */
	private File baseOutDir;

	/**
	 * Whether Rezenerator should be verbose or not
	 */
	private boolean verbose = true;

	/**
	 * If set to true, all files will be regenerated.
	 */
	private boolean forceUpdate = false;

	/**
	 * Packages containing extra processors
	 */
	private List<String> scannedPackages = new LinkedList<String>();

	/**
	 * Logger to use
	 */
	private Logger logger = new NoopLogger();

	/**
	 * Directories containing definition files. If a definition is not found in
	 * them, Rezenerator will try to find it in the classpath
	 */
	private List<File> definitionDirs = new LinkedList<File>();

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

	public List<File> getDefinitionDirs() {
		return definitionDirs;
	}

	public Configuration addDefinitionDir(File definitionDir) {
		this.definitionDirs.add(definitionDir);
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
