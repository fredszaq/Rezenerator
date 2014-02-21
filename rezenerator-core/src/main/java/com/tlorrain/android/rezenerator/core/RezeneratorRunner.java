package com.tlorrain.android.rezenerator.core;

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
import com.tlorrain.android.rezenerator.core.definition.DefinitionFinder;
import com.tlorrain.android.rezenerator.core.definition.DefinitionReader;
import com.tlorrain.android.rezenerator.core.log.Logger;
import com.tlorrain.android.rezenerator.core.processor.Processor;
import com.tlorrain.android.rezenerator.core.utils.JPGFileUtils;
import com.tlorrain.android.rezenerator.core.utils.PNGFileUtils;

public class RezeneratorRunner {

	private static final String DRAWABLE_PREFIX = "drawable-";

	public RunResult run(final Configuration configuration) {
		final Logger logger = configuration.getLogger();
		final RunResult result = new RunResult();
		final File inDir = configuration.getInDir();
		final File baseOutDir = configuration.getBaseOutDir();
		final DefinitionFinder finder = new DefinitionFinder(configuration.getDefinitionDirs());
		final Map<String, Processor> processors = loadProcessors(configuration.getScannedPackages(),
				configuration.getLogger());
		if (!inDir.exists() || !inDir.isDirectory()) {
			throw new IllegalStateException(inDir.getName() + " doesn't exist or is not a directory !");
		}
		boolean successful = true;
		for (final File inFile : inDir.listFiles()) {
			logger.info("Processing file: " + inFile.getName());
			try {
				final String[] nameSplit = splitFileName(inFile);
				final DefinitionReader definitionReader = new DefinitionReader(finder.find(nameSplit[1]));
				final Processor processor = getProcessor(processors, nameSplit[2]);
				final String outFileName = nameSplit[0] + "." + processor.extension();

				final Set<Entry<String, Dimensions>> entrySet = definitionReader.getConfigurations().entrySet();
				for (final Entry<String, Dimensions> entry : entrySet) {
					final File outFile = getOutFile(baseOutDir, entry.getKey(), outFileName);
					if (configuration.isForceUpdate() || shouldProcess(inFile, outFile, entry.getValue())) {
						successful &= processor.process(inFile, outFile, entry.getValue(), logger);
					} else {
						logger.info("Skipping " + outFile);
					}
				}
			} catch (final Exception e) {
				logger.error(e.getMessage());
				logger.verbose(e);
				result.addError(inFile, e);
				successful = false;
			}

		}
		logger.info("Rezenerator processing done !");
		if (!successful) {
			logger.error("There were some errors !");
		}
		result.setSuccessful(successful);
		return result;
	}

	private Processor getProcessor(final Map<String, Processor> processors, final String processorName) {
		final Processor processor = processors.get(processorName);
		if (processor == null) {
			throw new IllegalArgumentException("Could not find processor " + processorName);
		}
		return processor;
	}

	private String[] splitFileName(final File inFile) {
		final String[] nameSplit = inFile.getName().split("\\.");
		if (nameSplit.length != 4) {
			throw new IllegalArgumentException(
					"Filename must be formated this way : android_id.definition.processor.ext");

		}
		return nameSplit;
	}

	private boolean shouldProcess(final File inFile, final File outFile, final Dimensions dims) throws IOException {
		if (!(outFile.exists() && outFile.lastModified() > inFile.lastModified())) {
			return true;
		}
		if (outFile.getName().endsWith(".png")) {
			return !dims.equals(PNGFileUtils.getDimensions(outFile));
		}
		if (outFile.getName().endsWith(".jpg")) {
			return !dims.equals(JPGFileUtils.getDimensions(outFile));
		}
		return false; // We don't know the file type and cannot check its size,
						// lets assume we're good
	}

	private Map<String, Processor> loadProcessors(final List<String> scannedPackages, final Logger logger) {
		final Map<String, Processor> processors = new HashMap<String, Processor>();
		try {
			for (final String packageName : scannedPackages) {
				final Reflections reflections = new Reflections(packageName);
				for (final Class<?> processorClass : reflections.getSubTypesOf(Processor.class)) {
					if (!Modifier.isAbstract(processorClass.getModifiers())) {
						processors.put(converName(processorClass), (Processor) processorClass.newInstance());
						logger.verbose("loaded processor: " + processorClass);
					}
				}

			}
		} catch (final Exception e) {
			throw new RuntimeException("Could not init Rezenerator: " + e + ": " + e.getMessage(), e);
		}
		return processors;

	}

	private String converName(final Class<?> clazz) {
		return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
	}

	private File getOutFile(final File baseOutDir, final String qualifier, final String bareFileName) {
		final File outDir = new File(baseOutDir, DRAWABLE_PREFIX + qualifier);
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
		return new File(outDir, bareFileName);
	}
}
