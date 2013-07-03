package com.tlorrain.android.rezenerator.core;

import java.io.File;

public abstract class ExternalProcessProcessor implements Processor {

	// TODO add a timeout ?

	public boolean process(File inFile, File outFile, Dimensions outDims) {
		return ExternalProcessUtils.executeProcess(getProcessBuilder(inFile, outFile, outDims)) == 0;
	}

	public abstract ProcessBuilder getProcessBuilder(File inFile, File outFile, Dimensions outDims);

}
