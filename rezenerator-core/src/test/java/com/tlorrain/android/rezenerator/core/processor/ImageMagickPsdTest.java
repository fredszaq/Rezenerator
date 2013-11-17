package com.tlorrain.android.rezenerator.core.processor;

public class ImageMagickPsdTest extends ProcessorTestCase {

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Processor> Class<T> getProcessorClass() {
		return (Class<T>) ImageMagick.class;
	}

	@Override
	public String getBaseFileName() {
		return "ImageMagickPsdTest.psd";
	}

}
