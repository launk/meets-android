package com.theagilemonkeys.meets.magento.methods;

import com.theagilemonkeys.meets.magento.MageApiMethodCollectionResponseClasses;
import com.theagilemonkeys.meets.magento.RestApiMethod;
import com.theagilemonkeys.meets.models.base.MeetsFactory;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class Products extends RestApiMethod {
    public Products() {
        super(MeetsFactory.get().makeProduct().getClass());
    }
    public Products(boolean forCollection) {
        super(MeetsFactory.get().getApiMethodCollectionResponseClasses().productsMap());
    }
}

