package com.tlorrain.android.rezenerator.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RezeneratorRunner {

	private Map<String, DefinitionWrapper> definitions = new HashMap<String, DefinitionWrapper>();

	private Map<String, Processor> processors = new HashMap<String, Processor>();

	{
		definitions.put("launcher_icon", new DefinitionWrapper(LauncherIcon.class));
		processors.put("inkscape", new Inkscape());
	}

	public void run(Configuration configuration) {
		final File inDir = configuration.getInDir();
		final File baseOutDir = configuration.getBaseOutDir();
		if (!inDir.exists() || !inDir.isDirectory()) {
			throw new IllegalStateException(inDir.getName() + " doesn't exist or is not a directory !");
		}
		for (File inFile : inDir.listFiles()) {
			System.out.println("Processing file : " + inFile.getName());
			String[] nameSplit = inFile.getName().split("\\.");
			if (nameSplit.length != 4) {
				System.out.println("\tERROR : filename must be formated this way : android_id.definition.processor.ext");
				continue;
			}
			String bareFileName = nameSplit[0] + ".png";
			DefinitionWrapper definitionWrapper = definitions.get(nameSplit[1]);
			Processor processor = processors.get(nameSplit[2]);

			try {
				Set<Entry<String, Dimensions>> entrySet = definitionWrapper.getConfigurations().entrySet();
				for (Entry<String, Dimensions> entry : entrySet) {
					File outFile = getOutFile(baseOutDir, entry.getKey(), bareFileName);
					if (outFile.exists() && outFile.lastModified() > inFile.lastModified()) {
						System.out.println("skipping " + outFile);
						continue;
					}
					processor.process(inFile, outFile, entry.getValue());
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("Done !");
	}

	private File getOutFile(File baseOutDir, String qualifier, String bareFileName) {
		File outDir = new File(baseOutDir, "drawable-" + qualifier);
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
		return new File(outDir, bareFileName);
	}

	public static void main(String[] args) {
		new RezeneratorRunner().run(new Configuration().setInDir(new File("src/test/resources")).setBaseOutDir(new File("target/test")));
	}
}
