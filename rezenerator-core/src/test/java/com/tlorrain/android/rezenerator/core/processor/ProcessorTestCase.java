package com.tlorrain.android.rezenerator.core.processor;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.tlorrain.android.rezenerator.core.Dimensions;
import com.tlorrain.android.rezenerator.core.log.NoopLogger;
import com.tlorrain.android.rezenerator.core.utils.JPGFileUtils;
import com.tlorrain.android.rezenerator.core.utils.PNGFileUtils;

public abstract class ProcessorTestCase {

	private final File outDir = new File("target/processor-tests/" + this.getClass().getSimpleName());

	@Before
	public void setup() throws Exception {
		outDir.mkdirs();
		FileUtils.deleteDirectory(outDir);
		outDir.mkdir();
	}

	@Test
	public void test() throws Exception {
		final String baseFileName = getBaseFileName();
		final String[] split = baseFileName.split("\\.");
		final Processor processor = getProcessorClass().newInstance();
		final File outFile = new File(outDir, split[0] + "." + processor.extension());
		final Dimensions outDims = new Dimensions(512);
		processor.process(new File("src/test/resources/processor/" + baseFileName), outFile, outDims, new NoopLogger());
		if (processor.extension().equals("png")) {
			assertThat(PNGFileUtils.getDimensions(outFile)).isEqualTo(outDims);
		} else {
			// No check that we are truly a JPG, this will fail is we add
			// support for an other extension
			assertThat(JPGFileUtils.getDimensions(outFile)).isEqualTo(outDims);
		}
	}

	@Test(expected = RuntimeException.class)
	public void test_error() throws Exception {
		final File mockFile = Mockito.mock(File.class);
		Mockito.when(mockFile.getCanonicalPath()).thenThrow(new IOException());
		getProcessorClass().newInstance().process(mockFile, mockFile, new Dimensions(0), new NoopLogger());
	}

	public abstract String getBaseFileName();

	public abstract <T extends Processor> Class<T> getProcessorClass();

}
