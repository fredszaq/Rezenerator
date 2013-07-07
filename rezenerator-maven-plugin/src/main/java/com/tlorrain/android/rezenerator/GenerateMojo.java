package com.tlorrain.android.rezenerator;

import static org.apache.maven.plugins.annotations.LifecyclePhase.INITIALIZE;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.tlorrain.android.rezenerator.core.Configuration;
import com.tlorrain.android.rezenerator.core.RezeneratorRunner;

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
				.addScannedPackage("com.tlorrain.android");

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
}
