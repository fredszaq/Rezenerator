package com.tlorrain.android.rezenerator.core.processor;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.tlorrain.android.rezenerator.core.Dimensions;
import com.tlorrain.android.rezenerator.core.utils.PNGFileUtils;

public abstract class ProcessorTestCase {

	private File outDir = new File("target/" + this.getClass().getSimpleName() + "-tmp");

	@Before
	public void setup() throws Exception {
		outDir.mkdirs();
		FileUtils.deleteDirectory(outDir);
		outDir.mkdir();
	}

	@Test
	public void test() throws Exception {
		String baseFileName = getBaseFileName();
		String[] split = baseFileName.split("\\.");
		File outFile = new File(outDir, split[0] + ".png");
		Dimensions outDims = new Dimensions(512);
		getProcessorClass().newInstance().process(new File("src/test/resources/processor/" + baseFileName), outFile, outDims);
		assertThat(PNGFileUtils.getDimensions(outFile)).isEqualTo(outDims);
	}

	public abstract String getBaseFileName();

	public abstract <T extends Processor> Class<T> getProcessorClass();

}
