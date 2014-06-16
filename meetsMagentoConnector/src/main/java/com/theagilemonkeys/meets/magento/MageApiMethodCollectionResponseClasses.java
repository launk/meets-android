package com.theagilemonkeys.meets.magento;

import com.theagilemonkeys.meets.ApiMethodCollectionResponseClasses;
import com.theagilemonkeys.meets.magento.models.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class MageApiMethodCollectionResponseClasses implements ApiMethodCollectionResponseClasses{
    @Override
    public Class<? extends Map> productsMap() {
        return ProductsMap.class;
    }

    @Override
    public Class<? extends List> productsList() {
        return ProductsList.class;
    }

    @Override
    public Class<? extends List> categoriesList() {
        return Categories.class;
    }

    @Override
    public Class<? extends List> customersList() {
        return Customers.class;
    }

    @Override
    public Class<? extends List> shippingMethodsList() {
        return ShippingMethods.class;
    }

    @Override
    public Class<? extends List> paymentMethodsList() {
        return PaymentMethods.class;
    }

    @Override
    public Class<? extends List> cartItemsList() {
        return CartItems.class;
    }

    @Override
    public Class<? extends List> stockInfosList() {
        return StockInfos.class;
    }

    @Override
    public Class<? extends List> addressesList() {
        return Addresses.class;
    }

    public static class ProductsMap extends LinkedHashMap<Integer, MageMeetsProduct> {}
    public static class ProductsList extends ArrayList<MageMeetsProduct> {}
    public static class Categories extends ArrayList<MageMeetsCategory> {}
    public static class Customers extends ArrayList<MageMeetsCustomer> {}
    public static class ShippingMethods extends ArrayList<MageMeetsCartShipping> {}
    public static class PaymentMethods extends ArrayList<MageMeetsCartPayment> {}
    public static class CartItems extends ArrayList<MageMeetsCartItem> {}
    public static class StockInfos extends ArrayList<MageStockItem> {}
    public static class Addresses extends ArrayList<MageMeetsAddress> {}

}
