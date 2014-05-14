package com.theagilemonkeys.meets.magento.models;

import com.google.api.client.util.Key;
import com.theagilemonkeys.meets.models.MeetsCart;
import com.theagilemonkeys.meets.utils.soap.SoapSerializableObject;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class MageMeetsCartPayment extends SoapSerializableObject implements MeetsCart.Payment {

    @Key private String title;
    @Key private String po_number;
    @Key private String method;
    @Key private String code; //This is an alias of method. Magento use different name in get than in post
    @Key private String cc_cid;
    @Key private String cc_owner;
    @Key private String cc_number;
    @Key private String cc_type;
    @Key private String cc_exp_year;
    @Key private String cc_exp_month;
    @Key private String payment_token;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getCode() {
        return method != null ? method : code;
    }

    @Override
    public MeetsCart.Payment setPoNumber(String poNumber) {
        po_number = poNumber;
        return this;
    }

    @Override
    public MeetsCart.Payment setCode(String methodCode) {
        method = code = methodCode;
        return this;
    }

    @Override
    public MeetsCart.Payment setCcCid(String ccCid) {
        cc_cid = ccCid;
        return this;
    }

    @Override
    public MeetsCart.Payment setCcOwner(String ccOwner) {
        cc_owner = ccOwner;
        return this;
    }

    @Override
    public MeetsCart.Payment setCcNumber(String ccNumber) {
        cc_number = ccNumber;
        return this;
    }

    @Override
    public MeetsCart.Payment setCcType(String ccType) {
        cc_type = ccType;
        return this;
    }

    @Override
    public MeetsCart.Payment setCcExpYear(String ccExpYear) {
        cc_exp_year = ccExpYear;
        return this;
    }

    @Override
    public MeetsCart.Payment setCcExpMonth(String ccExpMonth) {
        cc_exp_month = ccExpMonth;
        return this;
    }

    @Override
    public MeetsCart.Payment setPaymentToken(String token) {
        payment_token = token;
        return this;
    }

}
