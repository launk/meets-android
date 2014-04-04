package com.theagilemonkeys.meets.magento.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.api.client.util.Key;
import com.theagilemonkeys.meets.models.MeetsProduct;
import com.theagilemonkeys.meets.utils.soap.Serializable;

import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class MageMeetsProductConfiguration extends Serializable.Object implements MeetsProduct.Configuration {
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
