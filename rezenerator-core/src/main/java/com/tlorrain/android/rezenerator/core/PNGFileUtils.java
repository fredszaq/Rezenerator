package com.tlorrain.android.rezenerator.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PNGFileUtils {

	public static Dimensions getDimensions(File outFile) throws FileNotFoundException, IOException {
		// this code is largely inspired from
		// http://blog.jaimon.co.uk/simpleimageinfo/SimpleImageInfo.java.html
		// credits to him (Apache 2 Licence)
		Dimensions dimensions = null;
		FileInputStream is = new FileInputStream(outFile);
		try {
			if (is.read() == 137 && is.read() == 80 && is.read() == 78) { // PNG
																			// file
				is.skip(15);
				int width = readIntBigEndianOnTwoBytes(is);
				is.skip(2);
				int height = readIntBigEndianOnTwoBytes(is);
				dimensions = new Dimensions(height, width);
			}
		} finally {
			is.close();
		}
		return dimensions;
	}

	private static int readIntBigEndianOnTwoBytes(InputStream is) throws IOException {
		int ret = 0;
		ret |= is.read() << 8;
		ret |= is.read();
		return ret;
	}

}
