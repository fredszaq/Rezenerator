package com.tlorrain.android.rezenerator.core.processor;

public class ImageMagickJpgJpgTest extends ProcessorTestCase {

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Processor> Class<T> getProcessorClass() {
		return (Class<T>) ImageMagickJpg.class;
	}

	@Override
	public String getBaseFileName() {
		return "ImageMagickJpgJpgTest.jpg";
	}

}
