package com.tlorrain.android.rezenerator.core.definition;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tlorrain.android.rezenerator.core.Dimensions;

public class DefinitionReader {

	private final Properties properties = new Properties();

	public DefinitionReader(final String fileName) throws FileNotFoundException, IOException {
		properties.load(this.getClass().getResourceAsStream(fileName));
	}

	public static final String PREFIX_VAL = "rezenerator.val.";
	public static final String PREFIX_DEF = "rezenerator.def.";

	public Map<String, Dimensions> getConfigurations() {
		final Map<String, Dimensions> result = new HashMap<String, Dimensions>();
		for (final String prop : properties.stringPropertyNames()) {
			if (prop.startsWith(PREFIX_VAL)) {
				result.put(prop.substring(PREFIX_VAL.length()), computeVal(prop));
			} else if (prop.startsWith(PREFIX_DEF)) {
				result.put(prop.substring(PREFIX_DEF.length()), computeDef(properties.getProperty(prop), result));
			}
		}
		return result;
	}

	private Dimensions computeVal(final String prop) {
		return Dimensions.fromString(properties.getProperty(prop));
	}

	private static final String IDENTIFIER = "[a-zA-Z][a-zA-Z\\-\\d]+";
	private static final Pattern METHOD_CALL = Pattern.compile("(" + IDENTIFIER + ")\\.(" + IDENTIFIER
			+ ")\\((\\d+)\\)");

	private Dimensions computeDef(final String property, final Map<String, Dimensions> result) {
		final Matcher matcher = METHOD_CALL.matcher(property);
		if (matcher.matches()) {
			try {
				final Method method = Dimensions.class.getMethod(matcher.group(2), int.class);
				final Dimensions target = computeVal(PREFIX_VAL + matcher.group(1));
				final int arg = Integer.parseInt(matcher.group(3));
				return (Dimensions) method.invoke(target, arg);
			} catch (final Exception e) {
				throw new IllegalArgumentException(property, e);
			}
		}
		throw new IllegalArgumentException(property);
	}
}
