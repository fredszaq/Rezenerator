package com.tlorrain.android.rezenerator.core.definition;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.util.Properties;

import org.fest.assertions.MapAssert.Entry;
import org.junit.Test;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class DefinitionReaderTest {

	@Test
	public void val() throws Exception {
		test(props( //
				"rezenerator.val.mdpi", "48", //
				"rezenerator.val.hdpi", "45x90"), //
				entry("mdpi", new Dimensions(48)), //
				entry("hdpi", new Dimensions(45, 90)));
	}

	@Test
	public void def_simple() throws Exception {
		test(props( //
				"rezenerator.val.mdpi", "48", //
				"rezenerator.def.xhdpi", "mdpi.multiply(2)"), //
				entry("mdpi", new Dimensions(48)), //
				entry("xhdpi", new Dimensions(96)));
	}

	@Test
	public void def_multiple() throws Exception {
		test(props( //
				"rezenerator.val.mdpi", "48", //
				"rezenerator.def.xhdpi", "mdpi.multiply(2)", //
				"rezenerator.def.hdpi", "mdpi.multiply(3).divide(2)"), //
				entry("mdpi", new Dimensions(48)), //
				entry("hdpi", new Dimensions(72)), //
				entry("xhdpi", new Dimensions(96)));
	}

	@Test
	public void def_def() throws Exception {
		test(props( //
				"rezenerator.val.mdpi", "48",//
				"rezenerator.def.xxhdpi", "mdpi.multiply(3)", //
				"rezenerator.def.hdpi", "xxhdpi.divide(2)"), //
				entry("mdpi", new Dimensions(48)), //
				entry("hdpi", new Dimensions(72)), //
				entry("xxhdpi", new Dimensions(144)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void def_illegal() throws Exception {
		test(props("rezenerator.def.xxhdpi", "mdpi.multiply(3)"));
	}

	public void test(final Properties properties, final Entry... contains) throws Exception {
		assertThat(new DefinitionReader(properties).getConfigurations()).hasSize(contains.length).includes(contains);
	}

	private Properties props(String... props) {
		Properties properties = new Properties();
		if (props.length % 2 != 0) {
			throw new RuntimeException();
		}
		for (int i = 0; i < props.length; i = i + 2) {
			properties.put(props[i], props[i + 1]);
		}
		return properties;
	}

}
