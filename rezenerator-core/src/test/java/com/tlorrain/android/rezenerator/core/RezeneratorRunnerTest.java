package com.tlorrain.android.rezenerator.core;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class RezeneratorRunnerTest {

	private File outDir = new File("target/RezeneratorRunnerTest-tmp");
	private RezeneratorRunner rezeneratorRunner = new RezeneratorRunner();
	private Configuration config = config();

	@Before
	public void setup() throws Exception {
		FileUtils.deleteDirectory(outDir);
	}

	@Test
	public void basic() throws Exception {
		rezeneratorRunner.run(config);
		checkOutDir(outDir);

	}

	@Test
	public void existingFiles() throws Exception {
		rezeneratorRunner.run(config);
		checkOutDir(outDir);
		File mdpi = getOutFile(outDir, "mdpi");
		File hdpi = getOutFile(outDir, "hdpi");
		File xhdpi = getOutFile(outDir, "xhdpi");
		File xxhdpi = getOutFile(outDir, "xxhdpi");

		long lastModifiedMdpi = mdpi.lastModified();
		long lastModifiedHdpi = hdpi.lastModified();
		long lastModifiedXhdpi = xhdpi.lastModified();
		long lastModifiedXxhdpi = xxhdpi.lastModified();
		Files.copy(xhdpi.toPath(), xxhdpi.toPath(), StandardCopyOption.REPLACE_EXISTING);

		Thread.sleep(1000); // time resolution on some files systems is 1s, so
							// lets wait that to be sure

		rezeneratorRunner.run(config);

		assertThat(mdpi.lastModified()).isEqualTo(lastModifiedMdpi);
		assertThat(hdpi.lastModified()).isEqualTo(lastModifiedHdpi);
		assertThat(xhdpi.lastModified()).isEqualTo(lastModifiedXhdpi);
		assertThat(xxhdpi.lastModified()).isGreaterThan(lastModifiedXxhdpi);

		checkOutDir(outDir);

	}

	private Configuration config() {
		return new Configuration().setInDir(new File("src/test/resources")).setBaseOutDir(outDir).addScannedPackage("com.tlorrain.android");
	}

	private void checkOutDir(File outDir) throws Exception {
		Map<String, Integer> dims = ImmutableMap.of("mdpi", 48, "hdpi", 72, "xhdpi", 96, "xxhdpi", 144);
		for (String subDir : dims.keySet()) {
			File outFile = getOutFile(outDir, subDir);
			assertThat(PNGFileUtils.getDimensions(outFile)).isEqualTo(new Dimensions(dims.get(subDir)));
		}

	}

	private File getOutFile(File outDir, String subDir) {
		return new File(outDir, "drawable-" + subDir + "/rezenerator.png");
	}

}
