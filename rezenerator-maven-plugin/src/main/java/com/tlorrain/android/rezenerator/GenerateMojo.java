package com.tlorrain.android.rezenerator;

import static org.apache.maven.plugins.annotations.LifecyclePhase.INITIALIZE;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.tlorrain.android.rezenerator.core.Configuration;
import com.tlorrain.android.rezenerator.core.RezeneratorRunner;
import com.tlorrain.android.rezenerator.core.RunResult;
import com.tlorrain.android.rezenerator.core.log.Logger;

/**
 * Goal generating png resources from source (svg...) files
 * 
 */
@Mojo(name = "generate", defaultPhase = INITIALIZE)
public class GenerateMojo extends AbstractMojo {

	/**
	 * Directory containing the source files.
	 * 
	 */
	@Parameter(required = true, defaultValue = "${project.basedir}/drawable")
	private File inputDirectory;

	/**
	 * Directory where the images will be generated.
	 * 
	 */
	@Parameter(required = true, defaultValue = "${project.basedir}/res")
	private File outputDirectory;

	/**
	 * Directory where the images will be cached to speed up the build.
	 * 
	 */
	@Parameter(defaultValue = "${project.basedir}/.rezenerator-cache~")
	private File cacheDirectory;

	/**
	 * Packages to scan for extra processors.
	 */
	@Parameter
	private List<String> scannedPackages;

	@Parameter
	private List<File> definitionDirs;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("input dir : " + inputDirectory);
		getLog().info("output dir : " + outputDirectory);
		getLog().info("cache dir : " + cacheDirectory);

		final Configuration configuration = new Configuration();

		configuration.setInDir(inputDirectory)//
				.setBaseOutDir(outputDirectory)//
				.addScannedPackage("com.tlorrain.android")// TODO add this by
															// default in conf
				.setLogger(new MavenLogger());

		if (scannedPackages != null) {
			for (final String pkg : scannedPackages) {
				getLog().info("add scanned package: " + pkg);
				configuration.addScannedPackage(pkg);
			}
		}

		if (definitionDirs != null) {
			for (final File dir : definitionDirs) {
				configuration.addDefinitionDir(dir);
			}
		} else {
			configuration.addDefinitionDir(new File("definitions"));
		}

		if (System.getProperties().getProperty("rezenerator.force.update") != null) {
			configuration.setForceUpdate(true);
		}

		final RunResult runResult = new RezeneratorRunner().run(configuration);
		if (!runResult.isSuccessful()) {
			if (runResult.getErrors().size() > 0) {
				final Entry<File, Exception> firstError = runResult.getErrors().entrySet().iterator().next();
				throw new MojoFailureException("Rezenerator reported " + runResult.getErrors().size()
						+ " errors, first one was on " + firstError.getKey() + " : "
						+ firstError.getValue().getMessage(), firstError.getValue());
			} else {
				// TODO make sure we never go, there :D
				throw new MojoFailureException("Rezenerator execution failed, no further details were provided");
			}

		}

	}

	private class MavenLogger implements Logger {

		@Override
		public void info(final String info) {
			getLog().info(info);
		}

		@Override
		public void verbose(final String debug) {
			getLog().debug(debug);
		}

		@Override
		public void verbose(final Exception exception) {
			getLog().debug(exception);
		}

		@Override
		public void error(final String error) {
			getLog().error(error);
		}

	}
}
