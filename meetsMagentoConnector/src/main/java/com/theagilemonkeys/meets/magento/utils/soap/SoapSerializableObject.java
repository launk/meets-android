package com.theagilemonkeys.meets.magento.utils.soap;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import static com.theagilemonkeys.meets.utils.Reflections.getAllFields;

public class SoapSerializableObject implements KvmSerializable {
    private static final java.util.List unserializableTypes = Arrays.asList(Float.class, float.class, Double.class, double.class);
    private transient java.util.List<Field> fields = null;
    private transient Object subject;

    public SoapSerializableObject(){
        subject = this;
    }

    public SoapSerializableObject(Object subject) {
        this.subject = subject;
    }

    private java.util.List<Field> getFields(){
        if (fields != null) return fields;

        fields = new ArrayList<Field>();

        for(Field field : getAllFields(subject.getClass()) ){
            int modifiers = field.getModifiers();
            if ( Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers) )
                continue;
            fields.add(field);
        }

        return fields;
    }


    private Object getPropertyValue(Field field) throws IllegalAccessException {
        // This is fix the serialization problems Soap has with doubles and floats
        Object val = field.get(subject);
        if ( unserializableTypes.contains(field.getType()) ) {
            val = String.valueOf(val);
        }
        return val;
    }

    @Override
    public Object getProperty(int i) {
        try{
            Field field = getFields().get(i);
            field.setAccessible(true);
            return getPropertyValue(field);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public int getPropertyCount() {
        return getFields().size();
    }

    @Override
    public void setProperty(int i, Object o) {
        try{
            Field field = getFields().get(i);
            field.setAccessible(true);
            field.set(subject, o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        Field field = getFields().get(i);

        //Check if this field has a specific name
        SoapAnnotations.PropertyName propertyName = field.getAnnotation(SoapAnnotations.PropertyName.class);
        if (propertyName != null)
            propertyInfo.setName(propertyName.value());
        else
            propertyInfo.setName(field.getName());

        propertyInfo.setType(field.getType());
    }
}