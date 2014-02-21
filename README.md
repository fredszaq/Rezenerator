# Rezenerator [![Build Status](https://travis-ci.org/fredszaq/Rezenerator.png?branch=master)](https://travis-ci.org/fredszaq/Rezenerator)

![Rezenerator icon](../../raw/master/rezenerator.png)

Rezenerator is a tool aiming at automating drawable management while building an Android app. The way it works is simple, you provide in a directory your "source" drawable files, such as SVG files, PSD files or high resolution PNG files, and the tool will automatically generate the corresponding PNG files in the required `res/drawable-*` folders of your application.

## Operating principle

Rezenerator is highly configurable and flexible. The transformations to apply to the sources file are encoded within the file names using this simple schema :

*android_id.definition_name.processor_name.ext*

* *android_id* is the android identifier of the resource, the generated files will have the name *android_id.png*
* *definition_name* is the definition to use to process this resource. A definition is a properties file telling to Rezenerator the sizes of the drawables to generate and the folders where to put them. Common definitions (launcher icons, notification icons...) are provided with the tool, and it is easy to create new ones
* *processor_name* is the processor to use. A `Processor` is a java class doing the conversion between the input file and the generated one. Rezenerator provides processors for common files format (SVG, PNG, ...) and you can provide yours as well
* *ext* is the extension of the source file, nothing new here ! 

Rezenerator is available as a standalone jar, a maven plugin and soon a gradle plugin.

## Definition syntax 
A definition is a properties file, here is an example : 

```properties
# get everything that was defined in base_drawable.properties
# base_drawable defines hdpi, xhdpi and xxhdpi images sizes in terms of the mdpi one
rezenerator.extends  = base_drawable

# the mdpi image is  42px x 24px  (height x width)
rezenerator.val.mdpi = 42x24

# ldpi image has the size of the hdpi one divided by two. 
rezenerator.def.ldpi = hdpi.divide(2) 
```


## Available processors
* ImageMagick for lots of formats (including PNG, PSD, JPG ...)
* ImageMagickJpg for lots of formats, with an output as a jpg file (usefull for photos)
* Inkscape for SVG files (and every other format Inkscape can open)
* InkscapeMagick for same formats as the Inkscape processor, but using a bit of ImageMagick for anti-aliasing

Please note that these processors use external tools that must be installed and accessible in the path.

## How to use it ? 
As this is still a development version, you'll have to compile it, just clone the repository and run a `mvn clean install`. You'll need to have Inkscape and ImageMagick (convert) in you path in order to be able to run the tests. You can skip tests by adding `-DskipTests` to the command line.

You have then two options :

### Standalone jar
You can use the standalone jar available in the `rezenerator-standalone/target/` directory. As you don't want to bother with dependencies, use `rezenerator-standalone-<version>-jar-with-dependencies.jar`. For now, the jar uses system properties for its configuration, just check the source code to see the ones supported !


### Maven plugin
You can also use the maven plugin, here is a basic example configuration to use in the pom of your project : 

```xml
<build>
	<plugins>
		<plugin>
			<groupId>com.tlorrain.android.rezenerator</groupId>
			<artifactId>rezenerator-maven-plugin</artifactId>
			<version>1.0-SNAPSHOT</version>
			<executions>
				<execution>
					<goals>
						<goal>generate</goal>
					</goals>
				</execution>
			</executions>
			<configuration>
				<!-- see the source for available options :D -->
			</configuration>
			<dependencies>
				<!-- any external dependency containing additional processors/definitions-->
			</dependencies>
		</plugin>
  </plugins>
</build>
```
By default, Rezenerator will process files in the `drawable` folder and output them in the correct `res/drawable-*` folder. 
      
For a more complete example, take a look at the `rezenerator-maven-sample` project. The configuration in the sample is more convenient: it will take over completely the management of the `res` folder (we use the `res` folder and not one in the `target` folder because otherwise the project cannot be imported in Eclipse). Files processed by Rezenerator are then located in the `src/main/android/drawable` directory. Every other files that should normally be placed in the `res` folder are in `src/main/android/res`. It is then easier to use a SCM (you just have to exclude the `res` folder) and you can use the `src/main/android/res` folder as an overlay (any file you put in there will still be available to Android and will take precedence over the ones Rezenerator generates). Note that `src/main/android/res` is processed by the `maven-resource-plugin` and you can use filtering on it !

The maven plugin works in Eclipse with m2e, you will have to refresh the project (F5) after adding a new image of after the first import.

## Note

This is still a work in progress !

## Licence

Copyright 2013 Thibaut Lorrain

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

