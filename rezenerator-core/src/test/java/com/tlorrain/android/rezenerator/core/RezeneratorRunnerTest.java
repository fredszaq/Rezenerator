package com.tlorrain.android.rezenerator.core;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.tlorrain.android.rezenerator.core.utils.PNGFileUtils;

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
		assertThat(rezeneratorRunner.run(config).isSuccessful()).isTrue();
		checkOutDir(outDir);
	}

	@Test
	public void error() throws Exception {
		assertThat(rezeneratorRunner.run(config.setInDir(new File("src/test/resources/runner-error"))).isSuccessful()).isFalse();
	}

	@Test
	public void existingFiles() throws Exception {
		rezeneratorRunner.run(config).isSuccessful();
		checkOutDir(outDir);
		File mdpi = getOutFile(outDir, "mdpi");
		File hdpi = getOutFile(outDir, "hdpi");
		File xhdpi = getOutFile(outDir, "xhdpi");
		File xxhdpi = getOutFile(outDir, "xxhdpi");

		long lastModifiedMdpi = mdpi.lastModified();
		long lastModifiedHdpi = hdpi.lastModified();
		long lastModifiedXhdpi = xhdpi.lastModified();
		long lastModifiedXxhdpi = xxhdpi.lastModified();
		FileUtils.copyFile(xhdpi, xxhdpi);

		Thread.sleep(1000); // time resolution on some files systems is 1s, so
							// lets wait that to be sure

		assertThat(rezeneratorRunner.run(config).isSuccessful()).isTrue();

		assertThat(mdpi.lastModified()).isEqualTo(lastModifiedMdpi);
		assertThat(hdpi.lastModified()).isEqualTo(lastModifiedHdpi);
		assertThat(xhdpi.lastModified()).isEqualTo(lastModifiedXhdpi);
		assertThat(xxhdpi.lastModified()).isGreaterThan(lastModifiedXxhdpi);

		checkOutDir(outDir);
	}

	@Test
	public void forceUpdate() throws Exception {
		rezeneratorRunner.run(config).isSuccessful();
		checkOutDir(outDir);
		File mdpi = getOutFile(outDir, "mdpi");
		File hdpi = getOutFile(outDir, "hdpi");
		File xhdpi = getOutFile(outDir, "xhdpi");
		File xxhdpi = getOutFile(outDir, "xxhdpi");

		long lastModifiedMdpi = mdpi.lastModified();
		long lastModifiedHdpi = hdpi.lastModified();
		long lastModifiedXhdpi = xhdpi.lastModified();
		long lastModifiedXxhdpi = xxhdpi.lastModified();

		Thread.sleep(1000); // time resolution on some files systems is 1s, so
							// lets wait that to be sure

		assertThat(rezeneratorRunner.run(config().setForceUpdate(true)).isSuccessful()).isTrue();

		assertThat(mdpi.lastModified()).isGreaterThan(lastModifiedMdpi);
		assertThat(hdpi.lastModified()).isGreaterThan(lastModifiedHdpi);
		assertThat(xhdpi.lastModified()).isGreaterThan(lastModifiedXhdpi);
		assertThat(xxhdpi.lastModified()).isGreaterThan(lastModifiedXxhdpi);

		checkOutDir(outDir);
	}

	private Configuration config() {
		return new Configuration().setInDir(new File("src/test/resources/runner")).setBaseOutDir(outDir).addScannedPackage("com.tlorrain.android");
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
