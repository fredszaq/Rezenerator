package com.tlorrain.android.rezenerator;

import static org.apache.maven.plugins.annotations.LifecyclePhase.INITIALIZE;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.tlorrain.android.rezenerator.core.Configuration;
import com.tlorrain.android.rezenerator.core.RezeneratorRunner;
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
	@Parameter(required = true, defaultValue = "${project.build.resources[0].directory}/drawable")
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

	public void execute() throws MojoExecutionException {
		getLog().info("input dir : " + inputDirectory);
		getLog().info("output dir : " + outputDirectory);
		getLog().info("cache dir : " + cacheDirectory);

		Configuration configuration = new Configuration();

		configuration.setInDir(inputDirectory)//
				.setBaseOutDir(outputDirectory)//
				.addScannedPackage("com.tlorrain.android")//
				.setLogger(new MavenLogger());

		String pakages = System.getProperties().getProperty("rezenerator.scanned.packages");
		if (pakages != null) {
			for (String pkg : pakages.split(",")) {
				configuration.addScannedPackage(pkg);
			}

		}

		if (System.getProperties().getProperty("rezenerator.force.update") != null) {
			configuration.setForceUpdate(true);
		}

		new RezeneratorRunner().run(configuration);

	}

	private class MavenLogger implements Logger {

		@Override
		public void info(String info) {
			GenerateMojo.this.getLog().info(info);
		}

		@Override
		public void verbose(String debug) {
			GenerateMojo.this.getLog().debug(debug);
		}

		@Override
		public void verbose(Exception exception) {
			GenerateMojo.this.getLog().debug(exception);
		}

		@Override
		public void error(String error) {
			GenerateMojo.this.getLog().error(error);
		}

	}
}
