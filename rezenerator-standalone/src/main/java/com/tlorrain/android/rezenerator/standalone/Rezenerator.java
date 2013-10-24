package com.tlorrain.android.rezenerator.standalone;

import java.io.File;

import com.tlorrain.android.rezenerator.core.Configuration;
import com.tlorrain.android.rezenerator.core.RezeneratorRunner;

public class Rezenerator {
	public static void main(final String[] args) {
		if (args.length < 2) {
			System.out.println("syntax <executable> inDir outDir");
			return;
		}
		final Configuration configuration = new Configuration();

		configuration.setInDir(new File(args[0]))//
				.setBaseOutDir(new File(args[1]))//
				.addScannedPackage("com.tlorrain.android")//
				.setLogger(new PrintStreamLogger(System.out, System.err));

		final String packages = System.getProperties().getProperty("rezenerator.scanned.packages");
		if (packages != null) {
			for (final String pkg : packages.split(",")) {
				configuration.addScannedPackage(pkg);
			}

		}
		final String definitionDirs = System.getProperties().getProperty("rezenerator.definition.dirs");
		if (definitionDirs != null) {
			for (final String def : definitionDirs.split(",")) {
				configuration.addDefinitionDir(new File(def));
			}

		}

		if (System.getProperties().getProperty("rezenerator.force.update") != null) {
			configuration.setForceUpdate(true);
		}

		new RezeneratorRunner().run(configuration).isSuccessful();
	}
}
