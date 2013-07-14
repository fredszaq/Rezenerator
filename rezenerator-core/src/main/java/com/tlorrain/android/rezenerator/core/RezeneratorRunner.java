package com.tlorrain.android.rezenerator.core;

import static com.tlorrain.android.rezenerator.core.utils.PNGFileUtils.getDimensions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.base.CaseFormat;
import com.tlorrain.android.rezenerator.core.definition.Definition;
import com.tlorrain.android.rezenerator.core.definition.DefinitionWrapper;
import com.tlorrain.android.rezenerator.core.log.Logger;
import com.tlorrain.android.rezenerator.core.processor.Processor;

public class RezeneratorRunner {

	private Map<String, DefinitionWrapper> definitions;

	private Map<String, Processor> processors;

	public boolean run(Configuration configuration) {
		boolean sucessful = true;
		final File inDir = configuration.getInDir();
		final File baseOutDir = configuration.getBaseOutDir();
		init(configuration.getScannedPackages());
		if (!inDir.exists() || !inDir.isDirectory()) {
			throw new IllegalStateException(inDir.getName() + " doesn't exist or is not a directory !");
		}
		Logger logger = configuration.getLogger();
		for (File inFile : inDir.listFiles()) {
			logger.info("Processing file : " + inFile.getName());
			String[] nameSplit = inFile.getName().split("\\.");
			if (nameSplit.length != 4) {
				logger.error("Filename must be formated this way : android_id.definition.processor.ext");
				sucessful = false;
				continue;
			}
			String bareFileName = nameSplit[0] + ".png";
			DefinitionWrapper definitionWrapper = definitions.get(nameSplit[1]);
			if (definitionWrapper == null) {
				logger.error("Could not find definition " + nameSplit[1]);
				sucessful = false;
				continue;
			}
			Processor processor = processors.get(nameSplit[2]);
			if (processor == null) {
				logger.error("Could not find processor " + nameSplit[2]);
				sucessful = false;
				continue;
			}

			try {
				Set<Entry<String, Dimensions>> entrySet = definitionWrapper.getConfigurations().entrySet();
				for (Entry<String, Dimensions> entry : entrySet) {
					File outFile = getOutFile(baseOutDir, entry.getKey(), bareFileName);
					if (configuration.isForceUpdate() || shouldProcess(inFile, outFile, entry.getValue())) {
						sucessful &= processor.process(inFile, outFile, entry.getValue(), logger);
					} else {
						logger.info("Skipping " + outFile);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				logger.verbose(e);
				sucessful = false;
			}

		}
		logger.info("Rezenerator processing done !");
		if (!sucessful) {
			logger.error("There were some errors !");
		}
		return sucessful;
	}

	private boolean shouldProcess(File inFile, File outFile, Dimensions dims) throws IOException {
		if (!(outFile.exists() && outFile.lastModified() > inFile.lastModified())) {
			return true;
		}

		return !dims.equals(getDimensions(outFile));
	}

	private void init(List<String> scannedPackages) {
		definitions = new HashMap<String, DefinitionWrapper>();
		processors = new HashMap<String, Processor>();
		try {
			for (String packageName : scannedPackages) {
				Reflections reflections = new Reflections(packageName);
				for (Class<?> definitionClass : reflections.getTypesAnnotatedWith(Definition.class)) {
					if (!Modifier.isAbstract(definitionClass.getModifiers())) {
						definitions.put(converName(definitionClass), new DefinitionWrapper(definitionClass));
					}
				}
				for (Class<?> processorClass : reflections.getSubTypesOf(Processor.class)) {
					if (!Modifier.isAbstract(processorClass.getModifiers())) {
						processors.put(converName(processorClass), (Processor) processorClass.newInstance());
					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Could not init Rezenerator", e);
		}

	}

	private String converName(Class<?> clazz) {
		return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
	}

	private File getOutFile(File baseOutDir, String qualifier, String bareFileName) {
		File outDir = new File(baseOutDir, "drawable-" + qualifier);
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
		return new File(outDir, bareFileName);
	}
}
