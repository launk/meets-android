package com.theagilemonkeys.meets.magento.methods;

import com.theagilemonkeys.meets.magento.SoapApiMethod;
import com.theagilemonkeys.meets.magento.models.MageMeetsProductConfigurationsData;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class CatalogProductGetSuperAttributes extends SoapApiMethod {
    public CatalogProductGetSuperAttributes() {
        super(MageMeetsProductConfigurationsData.class, true);
    }
}

