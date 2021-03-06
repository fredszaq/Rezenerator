package com.tlorrain.android.rezenerator.core.utils;

import static com.tlorrain.android.rezenerator.core.utils.StreamUtils.readIntBigEndianOnTwoBytes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class PNGFileUtils {

	public static Dimensions getDimensions(final File outFile) throws FileNotFoundException, IOException {
		// this code is largely inspired from
		// http://blog.jaimon.co.uk/simpleimageinfo/SimpleImageInfo.java.html
		// credits to him (Apache 2 Licence)
		Dimensions dimensions = null;
		final FileInputStream is = new FileInputStream(outFile);
		try {
			// PNG file
			if (is.read() == 137 && is.read() == 80 && is.read() == 78) {
				is.skip(15);
				final int width = readIntBigEndianOnTwoBytes(is);
				is.skip(2);
				final int height = readIntBigEndianOnTwoBytes(is);
				dimensions = new Dimensions(height, width);
			}
		} finally {
			IOUtils.closeQuietly(is);
		}
		return dimensions;
	}

}
