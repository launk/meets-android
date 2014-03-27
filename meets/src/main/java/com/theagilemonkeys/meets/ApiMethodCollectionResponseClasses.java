package com.theagilemonkeys.meets;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public interface ApiMethodCollectionResponseClasses {
    public static class GenericMap extends HashMap<String, Object> {}
    public static class ListOfGenericMaps extends ArrayList<GenericMap> {}


//    public static class ProductsMap extends LinkedHashMap<Integer, MageMeetsProduct> {}
//    public static class ProductsArray extends ArrayList<MageMeetsProduct> {}
//    public static class Categories extends ArrayList<MageMeetsCategory> {}
//    public static class Customers extends ArrayList<MageMeetsCustomer> {}
//    public static class ShippingMethods extends ArrayList<MageMeetsCartShipping> {}
//    public static class PaymentMethods extends ArrayList<MageMeetsCartPayment> {}
//    public static class CartItems extends ArrayList<MageMeetsCartItem> {}
//    public static class StockInfos extends ArrayList<MageStockItem> {}
//    public static class Addresses extends ArrayList<MageMeetsAddress> {}


    public Class productsMap();
    public Class productsList();
    public Class categoriesList();
    public Class customersList();
    public Class shippingMethodsList();
    public Class paymentMethodsList();
    public Class cartItemList();
    public Class stockInfosList();
    public Class addressesList();
}
