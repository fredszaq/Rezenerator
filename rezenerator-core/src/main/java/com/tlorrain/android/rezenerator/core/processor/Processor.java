package com.tlorrain.android.rezenerator.core.processor;

import java.io.File;

import com.tlorrain.android.rezenerator.core.Dimensions;

public interface Processor {

	boolean process(File inFile, File outFile, Dimensions outDims);

}
