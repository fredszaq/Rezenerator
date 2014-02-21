package com.tlorrain.android.rezenerator.core.utils;

import java.io.File;

import org.fest.assertions.Assertions;
import org.junit.Test;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class PNGFileUtilsTest {

	@Test
	public void getDimensions_validPNGFile() throws Exception {
		Assertions.assertThat(PNGFileUtils.getDimensions(new File("src/test/resources/utils/png512x512.png")))
				.isEqualTo(new Dimensions(512));
	}

	@Test
	public void getDimensions_invalidFile() throws Exception {
		Assertions.assertThat(PNGFileUtils.getDimensions(new File("src/test/resources/utils/jpg500x500.jpg"))).isNull();
	}

}
