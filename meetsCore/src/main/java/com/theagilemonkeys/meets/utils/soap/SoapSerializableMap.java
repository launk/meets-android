package com.theagilemonkeys.meets.utils.soap;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class SoapSerializableMap<KEY_TYPE, VALUE_TYPE> extends LinkedHashMap<KEY_TYPE, VALUE_TYPE> implements KvmSerializable {
    private transient java.util.List<VALUE_TYPE> values = null;
    private transient java.util.List<KEY_TYPE> keys = null;

    private java.util.List<VALUE_TYPE> getMapValues(){
        if (values != null) return values;
        values = new ArrayList<VALUE_TYPE>(values());
        return values;
    }

    private java.util.List<KEY_TYPE> getMapKeys(){
        if (keys != null) return keys;
        keys = new ArrayList<KEY_TYPE>(keySet());
        return keys;
    }

    @Override
    public Object getProperty(int i) {
        return getMapValues().get(i);
    }

    @Override
    public int getPropertyCount() {
        return size();
    }

    @Override
    public void setProperty(int i, Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        KEY_TYPE key = getMapKeys().get(i);
        propertyInfo.setName(String.valueOf(key));
        propertyInfo.setType(get(key).getClass());
    }
}