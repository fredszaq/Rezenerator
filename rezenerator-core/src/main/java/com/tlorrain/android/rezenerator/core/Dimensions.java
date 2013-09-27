package com.tlorrain.android.rezenerator.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dimensions {
	private final int height, width;

	public Dimensions(final int height, final int width) {
		this.height = height;
		this.width = width;
	}

	public Dimensions(final int squareSize) {
		this(squareSize, squareSize);
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

	public Dimensions scaleToWidth(final int newWidth) {
		return new Dimensions(height * newWidth / width, newWidth);
	}

	public Dimensions scaleToHeight(final int newHeight) {
		return new Dimensions(newHeight, width * newHeight / height);
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
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Dimensions other = (Dimensions) obj;
		if (height != other.height) {
			return false;
		}
		if (width != other.width) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Dimensions [height=" + height + ", width=" + width + "]";
	}

	private static final Pattern PATTERN_SIMPLE = Pattern.compile("\\d+");
	private static final Pattern PATTERN_X = Pattern.compile("(\\d+)[xX](\\d+)");

	public static Dimensions fromString(final String string) {
		if (string.matches(PATTERN_SIMPLE.pattern())) {
			return new Dimensions(Integer.parseInt(string));
		} else if (string.matches(PATTERN_X.pattern())) {
			final Matcher matcher = PATTERN_X.matcher(string);
			matcher.find();
			return new Dimensions(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
		}
		throw new IllegalArgumentException(string);
	}
}
