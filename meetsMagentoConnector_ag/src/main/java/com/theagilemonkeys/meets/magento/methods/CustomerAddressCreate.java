package com.theagilemonkeys.meets.magento.methods;

import com.theagilemonkeys.meets.magento.SoapApiMethod;
import com.theagilemonkeys.meets.magento.models.MageMeetsAddress;
import com.theagilemonkeys.meets.models.base.MeetsFactory;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class CustomerAddressCreate extends SoapApiMethod {
    public CustomerAddressCreate() {
        super(MeetsFactory.get().makeAddress().getClass());
    }

    @Override
    protected void parseResponse(Object response, Object model) throws Exception {
        ((MageMeetsAddress) model).setId((Integer) response);
    }
}

