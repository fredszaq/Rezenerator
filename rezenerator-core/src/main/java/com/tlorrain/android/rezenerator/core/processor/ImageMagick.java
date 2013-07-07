package com.tlorrain.android.rezenerator.core.processor;

import java.io.File;
import java.io.IOException;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class ImageMagick extends ExternalProcessProcessor {
	@Override
	public ProcessBuilder getProcessBuilder(File inFile, File outFile, Dimensions outDims) {
		try {
			return new ProcessBuilder("convert", inFile.getCanonicalPath(), //
					"-resize", outDims.getWidth() + "x" + outDims.getHeight(), //
					outFile.getCanonicalPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
