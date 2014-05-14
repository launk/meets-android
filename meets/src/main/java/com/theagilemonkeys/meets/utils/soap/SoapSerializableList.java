package com.theagilemonkeys.meets.utils.soap;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

public class SoapSerializableList<TYPE> extends ArrayList<TYPE> implements KvmSerializable {
    protected String listItemName = "item";

    public SoapSerializableList() {}

    public SoapSerializableList(Collection<TYPE> c) {
        super(c);
    }

    @Override
    public java.lang.Object getProperty(int i) {
        return get(i);
    }

    @Override
    public int getPropertyCount() {
        return size();
    }

    @Override
    public void setProperty(int i, java.lang.Object o) {
        add((TYPE) o);
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        propertyInfo.setName(listItemName);
        propertyInfo.setType(get(i).getClass());
    }
}