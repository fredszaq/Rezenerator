package com.tlorrain.android.rezenerator.core;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

public class DefinitionWrapper {

	private final Class<?> definitionClass;

	public DefinitionWrapper(Class<?> definitionClass) {
		super();
		this.definitionClass = definitionClass;
	}

	public Map<String, Dimensions> getConfigurations() throws IllegalArgumentException, IllegalAccessException {
		Map<String, Dimensions> result = new TreeMap<String, Dimensions>();
		Field[] fields = definitionClass.getFields();
		for (Field field : fields) {
			if (field.getAnnotation(Qualifier.class) != null) {
				result.put(field.getName(), (Dimensions) field.get(null));
			}
		}
		return result;
	}

}
