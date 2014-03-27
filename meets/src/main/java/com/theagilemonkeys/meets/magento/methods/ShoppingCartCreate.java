package com.theagilemonkeys.meets.magento.methods;

import com.theagilemonkeys.meets.magento.SoapApiMethod;
import com.theagilemonkeys.meets.magento.models.MageMeetsCart;
import com.theagilemonkeys.meets.models.base.MeetsFactory;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class ShoppingCartCreate extends SoapApiMethod {
    public ShoppingCartCreate() {
        super(MeetsFactory.get().makeCart().getClass());
    }

    @Override
    protected void parseResponse(Object response, Object model) throws Exception {
        ((MageMeetsCart) model).setId((Integer) response);
    }
}

