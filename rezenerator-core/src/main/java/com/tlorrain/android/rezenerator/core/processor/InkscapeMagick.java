package com.tlorrain.android.rezenerator.core.processor;

import java.io.File;

import com.tlorrain.android.rezenerator.core.Dimensions;
import com.tlorrain.android.rezenerator.core.log.Logger;

/**
 * A processor that uses Inkscape to get an image twice the required size and
 * then shrink it down using Image Magick. This may come in handy if the
 * {@link Inkscape} processor output aliased images
 * 
 */
public class InkscapeMagick extends BaseProcessor {

	@Override
	public boolean process(final File inFile, final File outFile, final Dimensions outDims, final Logger logger) {
		return new Inkscape().process(inFile, outFile, outDims.multiply(2), logger) //
				&& new ImageMagick().process(outFile, outFile, outDims, logger);
	}

}
