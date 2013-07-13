package com.tlorrain.android.rezenerator.core.processor;

import java.io.File;

import com.tlorrain.android.rezenerator.core.Dimensions;
import com.tlorrain.android.rezenerator.core.log.Logger;
import com.tlorrain.android.rezenerator.core.utils.ExternalProcessUtils;

public abstract class ExternalProcessProcessor implements Processor {

	// TODO add a timeout ?

	public boolean process(File inFile, File outFile, Dimensions outDims, Logger logger) {
		return ExternalProcessUtils.executeProcess(getProcessBuilder(inFile, outFile, outDims), logger) == 0;
	}

	public abstract ProcessBuilder getProcessBuilder(File inFile, File outFile, Dimensions outDims);

}
