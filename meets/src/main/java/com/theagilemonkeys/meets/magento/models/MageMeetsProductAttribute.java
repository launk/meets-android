package com.theagilemonkeys.meets.magento.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.api.client.util.Key;
import com.theagilemonkeys.meets.models.MeetsProduct;
import com.theagilemonkeys.meets.utils.soap.Serializable;

import java.util.ArrayList;
import java.util.List;

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
public class MageMeetsProductAttribute extends Serializable.Object implements MeetsProduct.Attribute {
    @Key int id;
    @Key String label;
    @Key
    @JsonDeserialize(as = ArrayList.class, contentAs = MageOption.class)
    List<Option> options;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public List<Option> getOptions() {
        return options;
    }

    @JsonAutoDetect(
            fieldVisibility = JsonAutoDetect.Visibility.ANY,
            getterVisibility = JsonAutoDetect.Visibility.NONE,
            isGetterVisibility = JsonAutoDetect.Visibility.NONE,
            setterVisibility = JsonAutoDetect.Visibility.NONE
    )
    static public class MageOption implements MeetsProduct.Attribute.Option {

        @Key int id;
        @Key String label;
        @Key double price;
        @Key boolean is_percent;

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public double getPriceOffset() {
            return price;
        }

        @Override
        public boolean isPercentageOffset() {
            return is_percent;
        }

    }
}
