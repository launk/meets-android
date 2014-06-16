package com.theagilemonkeys.meets.magento.methods;

import com.theagilemonkeys.meets.magento.SoapApiMethod;
import com.theagilemonkeys.meets.models.base.MeetsFactory;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class CatalogProductInfo extends SoapApiMethod {
    public CatalogProductInfo() {
        super(MeetsFactory.get().makeProduct().getClass());
    }
}

