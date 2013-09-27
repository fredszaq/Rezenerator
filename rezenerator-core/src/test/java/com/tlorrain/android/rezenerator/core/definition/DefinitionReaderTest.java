package com.tlorrain.android.rezenerator.core.definition;

import static org.fest.assertions.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class DefinitionReaderTest {

	@Test
	public void simple() throws FileNotFoundException, IOException {
		final DefinitionReader definitionReader = new DefinitionReader("/definition/simple_definition.properties");
		final Map<String, Dimensions> configurations = definitionReader.getConfigurations();
		assertThat(configurations).hasSize(2);
		System.out.println(configurations);
		assertThat(configurations.get("mdpi")).isEqualTo(new Dimensions(48));
		assertThat(configurations.get("hdpi")).isEqualTo(new Dimensions(45, 90));

	}

	@Test
	public void standard() throws FileNotFoundException, IOException {
		final DefinitionReader definitionReader = new DefinitionReader("/definition/standard_definition.properties");
		final Map<String, Dimensions> configurations = definitionReader.getConfigurations();
		assertThat(configurations).hasSize(2);
		System.out.println(configurations);
		assertThat(configurations.get("mdpi")).isEqualTo(new Dimensions(48));
		assertThat(configurations.get("xhdpi")).isEqualTo(new Dimensions(96, 96));

	}

}
