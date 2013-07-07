package com.tlorrain.android.rezenerator.core.processor;

import java.io.File;
import java.io.IOException;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class Inkscape extends ExternalProcessProcessor {
	@Override
	public ProcessBuilder getProcessBuilder(File inFile, File outFile, Dimensions outDims) {
		try {
			return new ProcessBuilder("inkscape", "-z", //
					"-e", outFile.getCanonicalPath(), //
					"-w", "" + outDims.getWidth(), //
					"-h", "" + outDims.getHeight(), //
					inFile.getCanonicalPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
