package com.theagilemonkeys.meets.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class Reflections {
    /**
     * Return the set of fields declared at all level of class hierachy
     */
    static public List<Field> getAllFields(Class clazz) {
        List<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));

        Class superClazz = clazz.getSuperclass();
        if(superClazz != null) {
            fields.addAll(getAllFields(superClazz));
        }
        return fields;
    }
}
