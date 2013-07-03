package com.tlorrain.android.rezenerator.core;

import java.io.File;

public interface Processor {

	boolean process(File inFile, File outFile, Dimensions outDims);

}
