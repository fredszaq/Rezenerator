# Rezenerator [![Build Status](https://travis-ci.org/fredszaq/Rezenerator.png?branch=master)](https://travis-ci.org/fredszaq/Rezenerator)

![Rezenerator icon](../../raw/master/rezenerator.png)

Rezenerator is a tool aiming at automating drawable management while building an Android app. The way it works is simple, you provide in a directory your "source" drawable files, such as SVG files, PSD files or high resolution PNG files, and the tool will automatically generate the corresponding PNG files in the required drawable-* folders of your application.

## Operating principle

Rezenerator is highly configurable and flexible. The transformations to apply to the sources file are encoded within the file names using this simple schema :

*android_id.definition_name.processor_name.ext*

* android_id is the android identifier of the resource, the files generated will have the name android_id.png
* definition_name is the definition to use to process this resource. A `Definition` is an annotated Java file telling to Rezenerator the sizes of the drawables to generate and the folders where to put them. Common definitions (launcher icons, notification icons...) are provided with the tool, and it is easy to create new ones
* processor_name is the processor to use. A `Processor` is a java class doing the conversion between the input file and the generated one. Rezenerator provides processors for common files format (SVG, PNG, ...) and you can provide yours as well
* ext is the extention of the source file, nothing new here ! 

Rezenerator is available as a standalone jar, a maven plugin and soon a graddle plugin.

## Available processors
* Inkscape for SVG files (an every other forma inkscape can open)
* ImageMagick for lots of formats (including PNG, PSD ...)
Please note that these processors use extrenal tools that must be installed and accessible in the path.



## Note

This is still a work in progress !
