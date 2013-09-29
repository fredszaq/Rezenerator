package com.tlorrain.android.rezenerator.core.definition;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.util.Map;

import org.fest.assertions.MapAssert.Entry;
import org.junit.Test;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class DefinitionReaderTest {

	@Test
	public void val() throws Exception {
		test("val", //
				entry("mdpi", new Dimensions(48)), //
				entry("hdpi", new Dimensions(45, 90)));
	}

	@Test
	public void def_simple() throws Exception {
		test("def_simple", //
				entry("mdpi", new Dimensions(48)), //
				entry("xhdpi", new Dimensions(96)));
	}

	@Test
	public void def_multiple() throws Exception {
		test("def_multiple", //
				entry("mdpi", new Dimensions(48)), //
				entry("hdpi", new Dimensions(72)), //
				entry("xhdpi", new Dimensions(96)));
	}

	@Test
	public void def_def() throws Exception {
		test("def_def", //
				entry("mdpi", new Dimensions(48)), //
				entry("hdpi", new Dimensions(72)), //
				entry("xxhdpi", new Dimensions(144)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void def_illegal() throws Exception {
		test("def_illegal");
	}

	public void test(final String fileName, final Entry... contains) throws Exception {
		final DefinitionReader definitionReader = new DefinitionReader("/definition/" + fileName + ".properties");
		final Map<String, Dimensions> configurations = definitionReader.getConfigurations();
		assertThat(configurations).hasSize(contains.length);
		System.out.println(configurations);
		assertThat(configurations).includes(contains);
	}

}
