package com.tlorrain.android.rezenerator.core.processor;

public class ImageMagickJpgTest extends ProcessorTestCase {

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Processor> Class<T> getProcessorClass() {
		return (Class<T>) ImageMagick.class;
	}

	@Override
	public String getBaseFileName() {
		return "ImageMagickJpgTest.jpg";
	}

}
