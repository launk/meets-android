package com.theagilemonkeys.meets.cloud.models.base;

import com.theagilemonkeys.meets.ApiMethodCollectionResponseClasses;
import com.theagilemonkeys.meets.magento.MageApiMethodCollectionResponseClasses;
import com.theagilemonkeys.meets.magento.methods.Products;
import com.theagilemonkeys.meets.models.*;
import com.theagilemonkeys.meets.models.base.MeetsCollection;
import com.theagilemonkeys.meets.models.base.MeetsFactory;

import java.util.List;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class MageMeetsFactory extends MeetsFactory {
    @Override
    public MeetsProduct makeProduct() {
        return new MageMeetsProduct();
    }

    @Override
    public MeetsProduct makeProduct(int id) {
        return makeProduct().setId(id);
    }

    @Override
    public MeetsCollection<MeetsProduct> makeProductCollection() {
        return new MageMeetsCollection(Products.class);
    }

    @Override
    public MeetsCategory makeCategory() {
        return new MageMeetsCategory();
    }

    @Override
    public MeetsCategory makeCategory(int id) {
        return makeCategory().setId(id);
    }

    @Override
    public MeetsCart makeCart() {
        return new MageMeetsCart();
    }

    @Override
    public MeetsCart makeCart(int id) {
        return makeCart().setId(id);
    }

    @Override
    public MeetsCart.Item makeCartItem() {
        return new MageMeetsCartItem();
    }

    @Override
    public MeetsCart.Item makeCartItem(MeetsProduct product) {
        return new MageMeetsCartItem().fillFromProduct(product);
    }

    @Override
    public MeetsCustomer makeCustomer() {
        return new MageMeetsCustomer();
    }

    @Override
    public MeetsCustomer makeCustomer(int id) {
        return makeCustomer().setId(id);
    }

    @Override
    public MeetsCollection<MeetsCustomer> makeCustomerCollection() {
        throw new UnsupportedOperationException("Still not implemented");
//        return new MageMeetsCollection<MeetsCustomer>(CustomerCustomerList.class);
    }

    @Override
    public MeetsAddress makeAddress() {
        return new MageMeetsAddress();
    }

    @Override
    public MeetsAddress makeAddress(int id) {
        return makeAddress().setId(id);
    }

    @Override
    public MeetsCart.Payment makeCartPayment() {
        return new MageMeetsCartPayment();
    }

    @Override
    public MeetsCart.Shipping makeCartShipping() {
        return new MageMeetsCartShipping();
    }

    @Override
    public MeetsStock.ItemList makeStockItemList() {
        return new MageStockInfoList();
    }

    @Override
    public MeetsStock.ItemList makeStockItemList(int id) {
        return makeStockItemList().setId(id);
    }

    @Override
    public MeetsStock.ItemList makeStockItemList(List<Integer> ids) {
        return makeStockItemList().setIds(ids);
    }

    @Override
    public ApiMethodCollectionResponseClasses getApiMethodCollectionResponseClasses() {
        return new MageApiMethodCollectionResponseClasses();
    }
}
