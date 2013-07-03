package com.tlorrain.android.rezenerator.core;

public class Dimensions {
	private final int height, width;

	public Dimensions(int height, int width) {
		this.height = height;
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Dimensions multiply(final int factor) {
		return new Dimensions(height * factor, width * factor);
	}

	public Dimensions divide(final int divisor) {
		return new Dimensions(height / divisor, width / divisor);
	}

}
