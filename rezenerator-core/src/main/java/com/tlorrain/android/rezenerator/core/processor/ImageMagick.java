package com.tlorrain.android.rezenerator.core.processor;

import java.io.File;
import java.io.IOException;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class ImageMagick extends ExternalProcessProcessor {
	@Override
	public ProcessBuilder getProcessBuilder(final File inFile, final File outFile, final Dimensions outDims) {
		try {
			return new ProcessBuilder("convert", inFile.getCanonicalPath(), //
					"-background", "#ffffff00", //
					"-resize", outDims.getWidth() + "x" + outDims.getHeight(), //
					"-layers", "flatten", //
					outFile.getCanonicalPath());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
