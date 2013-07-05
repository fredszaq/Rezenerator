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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dimensions other = (Dimensions) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

}
