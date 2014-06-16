package com.theagilemonkeys.meets.magento.soap;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class SoapAnnotations {

    /**
     * Annotation to allow specify the type of collections
     */
    public @Retention(RetentionPolicy.RUNTIME) @interface PropertyName {
        String value();
    }

    /**
     * Annotation to allow specify the type of collections
     */
    public @Retention(RetentionPolicy.RUNTIME) @interface ListInfo {
        Class value();
//        Class listClass() default ArrayList.class;
//        Class itemClass() default null;

    }
    /**
     * Annotation to allow specify the type of map
     */
    public @Retention(RetentionPolicy.RUNTIME) @interface MapType {
        Class value();
    }
}
