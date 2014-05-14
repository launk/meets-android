package com.theagilemonkeys.meets.magento.models;

import com.google.api.client.util.Key;
import com.theagilemonkeys.meets.models.MeetsProduct;
import com.theagilemonkeys.meets.utils.soap.SoapSerializableObject;

import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */

public class MageMeetsProductConfiguration extends SoapSerializableObject implements MeetsProduct.Configuration {
    @Key
    Map<Integer, Integer> configuration;
    @Key
    int stock;

    @Override
    public Map<Integer, Integer> getAttributeOptionMap() {
        return configuration;
    }

    @Override
    public int getStock() {
        return stock;
    }
}
