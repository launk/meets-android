package com.theagilemonkeys.meets.magento.methods;

import com.theagilemonkeys.meets.magento.SoapApiMethod;
import com.theagilemonkeys.meets.magento.models.MageMeetsCustomer;
import com.theagilemonkeys.meets.models.base.MeetsFactory;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class CustomerCustomerCreate extends SoapApiMethod {
    public CustomerCustomerCreate() {
        super(MeetsFactory.get().makeCustomer().getClass());
    }

    @Override
    protected void parseResponse(Object response, Object model) throws Exception {
        ((MageMeetsCustomer) model).setId((Integer) response);
    }
}

