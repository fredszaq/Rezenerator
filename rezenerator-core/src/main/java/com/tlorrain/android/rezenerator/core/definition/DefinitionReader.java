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
				result.put(prop.substring(PREFIX_DEF.length()), computeDef(prop));
			}
		}
		return result;
	}

	/**
	 * Computes a val from a property name. The string contained in the property
	 * must have a format that can be parsed by the
	 * {@link Dimensions#fromString(String)} method.
	 * 
	 * @param propName
	 *            the property containing the val to compute
	 * @return a {@link Dimensions} created from the string contained in the
	 *         property
	 */
	private Dimensions computeVal(final String propName) {
		return Dimensions.fromString(properties.getProperty(propName));
	}

	private static final String ID_REG = "[a-zA-Z][a-zA-Z\\-\\d]+";
	private static final String METHOD_REG = "(" + ID_REG + ")\\((\\d+)\\)";
	private static final String CALL_REG = "\\.";

	private static final Pattern METHOD = Pattern.compile(METHOD_REG);
	private static final Pattern CALLS = Pattern.compile("(" + ID_REG + ")(" + CALL_REG + METHOD_REG + ")+");

	/**
	 * Computes a def from a property name. The string contained in the property
	 * must have this format : valName[.methodOnDefinitions(int)]+
	 * 
	 * @param propName
	 *            the property containg the def to compute
	 * @return a {@link Dimensions} created by applying the method calls on the
	 *         val
	 */
	private Dimensions computeDef(final String propName) {
		final String property = properties.getProperty(propName);
		final Matcher multiMethodCallMatcher = CALLS.matcher(property);
		if (multiMethodCallMatcher.matches()) {
			final String[] methodsLiterals = property.split(CALL_REG);
			Dimensions result = computeVal(PREFIX_VAL + multiMethodCallMatcher.group(1));
			for (int i = 1; i < methodsLiterals.length; i++) {
				try {
					final Matcher methodCallMatcher = METHOD.matcher(methodsLiterals[i]);
					methodCallMatcher.matches();
					final Method method = Dimensions.class.getMethod(methodCallMatcher.group(1), int.class);
					final int arg = Integer.parseInt(methodCallMatcher.group(2));
					result = (Dimensions) method.invoke(result, arg);
				} catch (final Exception e) {
					throw new IllegalArgumentException(property, e);
				}
			}
			return result;
		}
		throw new IllegalArgumentException(property);
	}
}
