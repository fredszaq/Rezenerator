package com.tlorrain.android.rezenerator.core;

@Definition
public class LauncherIcon {
	// TODO extract a base class with an abstract method containing the mdpi
	// Dimensions and auto guessing the others

	@Qualifier
	public static final Dimensions mdpi = new Dimensions(48, 48);

	@Qualifier
	public static final Dimensions hdpi = mdpi.multiply(3).divide(2);

	@Qualifier
	public static final Dimensions xhdpi = mdpi.multiply(2);

	@Qualifier
	public static final Dimensions xxhdpi = mdpi.multiply(3);

}
