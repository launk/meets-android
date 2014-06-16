package com.theagilemonkeys.meets.magento.models;

import com.theagilemonkeys.meets.models.MeetsCart;
import com.theagilemonkeys.meets.utils.soap.SoapSerializableObject;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class MageMeetsCartShipping extends SoapSerializableObject implements MeetsCart.Shipping {

    private String code;
    private String carrier;
    private String carrier_title;
//    private String method;
    private String method_title;
    private String method_description;
    private double price;


    @Override
    public String getCarrierCode() {
        return carrier;
    }

    @Override
    public String getCarrierTitle() {
        return carrier_title;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public MeetsCart.Shipping setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String getTitle() {
        return method_title;
    }

    @Override
    public String getDescription() {
        return method_description;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
