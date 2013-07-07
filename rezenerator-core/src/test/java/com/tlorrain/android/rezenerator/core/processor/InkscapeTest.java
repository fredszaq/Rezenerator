package com.tlorrain.android.rezenerator.core.processor;

public class InkscapeTest extends ProcessorTestCase {

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Processor> Class<T> getProcessorClass() {
		return (Class<T>) Inkscape.class;
	}

	@Override
	public String getBaseFileName() {
		return "InkscapeTest.svg";
	}

}
