package com.theagilemonkeys.meets.magento.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.theagilemonkeys.meets.models.MeetsProduct;
import com.theagilemonkeys.meets.magento.utils.soap.SoapSerializableObject;

import java.util.List;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */

public class MageMeetsProductAttribute extends SoapSerializableObject implements MeetsProduct.Attribute {
    int id;
    String label;
    @JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="class", defaultImpl = MageOption.class)
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

    static public class MageOption implements Option {
        int id;
        String label;
        double price;
        boolean is_percent;

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
