package com.tlorrain.android.rezenerator.core.utils;

import static com.tlorrain.android.rezenerator.core.utils.StreamUtils.readIntBigEndianOnTwoBytes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class JPGFileUtils {

	public static Dimensions getDimensions(final File outFile) throws FileNotFoundException, IOException {
		// this code is largely inspired from
		// http://blog.jaimon.co.uk/simpleimageinfo/SimpleImageInfo.java.html
		// credits to him (Apache 2 Licence)
		Dimensions dimensions = null;
		final FileInputStream is = new FileInputStream(outFile);
		try {
			// JPG file
			if (is.read() == 0xFF && is.read() == 0xD8) {
				int height = -1;
				int width = -1;
				while (is.read() == 255) {
					final int marker = is.read();
					final int len = readIntBigEndianOnTwoBytes(is);
					if (marker == 192 || marker == 193 || marker == 194) {
						is.skip(1);
						height = readIntBigEndianOnTwoBytes(is);
						width = readIntBigEndianOnTwoBytes(is);
						break;
					}
					is.skip(len - 2);
				}
				if (height != -1 && width != -1) {
					dimensions = new Dimensions(height, width);
				}
			}
		} finally {
			IOUtils.closeQuietly(is);
		}
		return dimensions;
	}

}
