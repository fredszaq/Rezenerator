package com.tlorrain.android.rezenerator.core.processor;

public class InkscapeMagickTest extends ProcessorTestCase {

	@Override
	public String getBaseFileName() {
		return "InkscapeTest.svg";
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Processor> Class<T> getProcessorClass() {
		return (Class<T>) InkscapeMagick.class;
	}

}
