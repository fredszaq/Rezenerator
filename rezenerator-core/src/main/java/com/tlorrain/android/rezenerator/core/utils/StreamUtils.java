package com.tlorrain.android.rezenerator.core.utils;

import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

	static int readIntBigEndianOnTwoBytes(final InputStream is) throws IOException {
		int ret = 0;
		ret |= is.read() << 8;
		ret |= is.read();
		return ret;
	}

}
