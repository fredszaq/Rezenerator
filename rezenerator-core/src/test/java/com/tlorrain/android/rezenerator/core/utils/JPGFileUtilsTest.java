package com.tlorrain.android.rezenerator.core.utils;

import java.io.File;

import org.fest.assertions.Assertions;
import org.junit.Test;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class JPGFileUtilsTest {

	@Test
	public void getDimensions_validJPGFile() throws Exception {
		Assertions.assertThat(JPGFileUtils.getDimensions(new File("src/test/resources/utils/jpg500x500.jpg")))
				.isEqualTo(new Dimensions(500));
	}

	@Test
	public void getDimensions_invalidFile() throws Exception {
		Assertions.assertThat(JPGFileUtils.getDimensions(new File("src/test/resources/utils/png512x512.png"))).isNull();
	}

}
