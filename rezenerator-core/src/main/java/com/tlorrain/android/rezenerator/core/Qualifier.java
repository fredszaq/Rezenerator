package com.tlorrain.android.rezenerator.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {
	String name() default "";
}
