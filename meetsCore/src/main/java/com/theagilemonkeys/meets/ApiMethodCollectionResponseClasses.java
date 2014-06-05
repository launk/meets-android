package com.theagilemonkeys.meets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public interface ApiMethodCollectionResponseClasses {
    public static class GenericMap extends HashMap<String, Object> {}
    public static class ListOfGenericMaps extends ArrayList<GenericMap> {}

    public Class<? extends Map> productsMap();
    public Class<? extends List> productsList();
    public Class<? extends List> categoriesList();
    public Class<? extends List> customersList();
    public Class<? extends List> shippingMethodsList();
    public Class<? extends List> paymentMethodsList();
    public Class<? extends List> cartItemsList();
    public Class<? extends List> stockInfosList();
    public Class<? extends List> addressesList();
}
