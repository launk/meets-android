package com.theagilemonkeys.meets.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theagilemonkeys.meets.models.base.MeetsModel;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

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
