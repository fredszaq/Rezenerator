package com.tlorrain.android.rezenerator.core.definition;

import static com.google.common.collect.ImmutableList.of;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.io.File;
import java.util.Properties;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class DefinitionFinderTest {

	@Test
	public void fromClassPath() {
		DefinitionFinder definitionFinder = new DefinitionFinder(ImmutableList.<File> of());
		Properties find = definitionFinder.find("launcher_icon");
		assertThat(find).hasSize(4).includes(//
				entry("rezenerator.val.mdpi", "48"),//
				entry("rezenerator.def.hdpi", "xxhdpi.divide(2)"),//
				entry("rezenerator.def.xhdpi", "mdpi.multiply(2)"),//
				entry("rezenerator.def.xxhdpi", "mdpi.multiply(3)"));
	}

	@Test
	public void fromFileAndClasspath() {
		DefinitionFinder definitionFinder = new DefinitionFinder(of(new File("src/test/resources/definitions")));
		Properties find = definitionFinder.find("test");
		assertThat(find).hasSize(4).includes(//
				entry("rezenerator.val.mdpi", "42"),//
				entry("rezenerator.def.hdpi", "xxhdpi.divide(2)"),//
				entry("rezenerator.def.xhdpi", "mdpi.multiply(2)"),//
				entry("rezenerator.def.xxhdpi", "mdpi.multiply(3)"));
	}

}
