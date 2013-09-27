package com.tlorrain.android.rezenerator.core;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class DimensionsTest {

	@Test
	public void multiply() {
		assertThat(new Dimensions(2, 4).multiply(2)).isEqualTo(new Dimensions(4, 8));
	}

	@Test
	public void divide() {
		assertThat(new Dimensions(2, 4).divide(2)).isEqualTo(new Dimensions(1, 2));
	}

	@Test
	public void scaleToWidth() {
		assertThat(new Dimensions(2, 4).scaleToWidth(10)).isEqualTo(new Dimensions(5, 10));
	}

	@Test
	public void scaleToHeigth() {
		assertThat(new Dimensions(2, 4).scaleToHeight(10)).isEqualTo(new Dimensions(10, 20));
	}

	@Test
	public void fromString_square() {
		assertThat(Dimensions.fromString("50")).isEqualTo(new Dimensions(50));
	}

	@Test
	public void fromString_usex() {
		assertThat(Dimensions.fromString("50x60")).isEqualTo(new Dimensions(50, 60));
	}

	@Test
	public void fromString_useX() {
		assertThat(Dimensions.fromString("50X60")).isEqualTo(new Dimensions(50, 60));
	}

	@Test(expected = IllegalArgumentException.class)
	public void fromString_illegal() {
		Dimensions.fromString("toto");
	}

}
