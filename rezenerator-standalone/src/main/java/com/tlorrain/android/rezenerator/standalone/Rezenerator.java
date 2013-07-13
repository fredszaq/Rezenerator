package com.tlorrain.android.rezenerator.standalone;

import java.io.File;

import com.tlorrain.android.rezenerator.core.Configuration;
import com.tlorrain.android.rezenerator.core.RezeneratorRunner;

public class Rezenerator {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("syntax <executable> inDir outDir");
			return;
		}
		Configuration configuration = new Configuration();

		configuration.setInDir(new File(args[0]))//
				.setBaseOutDir(new File(args[1]))//
				.addScannedPackage("com.tlorrain.android")//
				.setLogger(new PrintStreamLogger(System.out, System.err));

		String pakages = System.getProperties().getProperty("rezenerator.scanned.packages");
		if (pakages != null) {
			for (String pkg : pakages.split(",")) {
				configuration.addScannedPackage(pkg);
			}

		}

		if (System.getProperties().getProperty("rezenerator.force.update") != null) {
			configuration.setForceUpdate(true);
		}

		new RezeneratorRunner().run(configuration);
	}
}
