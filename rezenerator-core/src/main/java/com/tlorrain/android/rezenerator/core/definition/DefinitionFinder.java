package com.tlorrain.android.rezenerator.core.definition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class DefinitionFinder {

	private static final String KEY_EXTENDS = "rezenerator.extends";
	List<File> searchedDirs;

	public DefinitionFinder(List<File> searchedDirs) {
		this.searchedDirs = searchedDirs;
	}

	public Properties find(String definitionName) {
		String fileName = definitionName + ".properties";

		// first, try to load from a file in the searched dirs
		for (File dir : searchedDirs) {
			File file = FileUtils.getFile(dir, fileName);
			if (file.exists()) {
				return loadFromFile(file, new Properties());
			}
		}
		// second approach, load from classpath
		Properties loadFromClasspath = loadFromClasspath(fileName);
		if (loadFromClasspath != null) {
			return loadFromClasspath;
		}
		throw new IllegalArgumentException("Could not find definition " + definitionName);

	}

	private Properties loadFromClasspath(String fileName) {
		InputStream fromClassPath = this.getClass().getClassLoader().getResourceAsStream(fileName);
		if (fromClassPath != null) {
			try {
				return load(fromClassPath, new Properties());
			} catch (IOException e) {
				throw new IllegalStateException("error while loading file from classpath : " + fileName, e);
			} finally {
				IOUtils.closeQuietly(fromClassPath);
			}
		}
		return null;
	}

	private Properties loadFromFile(File file, Properties properties) {
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
			properties = load(inStream, properties);

		} catch (IOException e) {
			throw new IllegalStateException("error while loading file : " + file, e);
		} finally {
			IOUtils.closeQuietly(inStream);
		}
		return properties;
	}

	private Properties load(InputStream inStream, Properties properties) throws IOException {
		properties.load(inStream);

		// load extends
		if (properties.containsKey(KEY_EXTENDS)) {
			Properties child = properties;
			properties = find(child.getProperty(KEY_EXTENDS));
			child.remove(KEY_EXTENDS);
			properties.putAll(child);
		}
		return properties;
	}

}
