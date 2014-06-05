package com.theagilemonkeys.meets.models;

import com.theagilemonkeys.meets.models.base.MeetsModel;

import java.util.List;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public interface MeetsStock {
    interface ItemList extends MeetsModel<ItemList> {
        ItemList fetch(List<Integer> ids);
        ItemList setIds(List<Integer> ids);
        List<Integer> getIds();
        List<Item> getList();
    }

    interface Item {
        int getProductId();
        String getProductSku();
        boolean isInStock();
        double getQuantity();
    }
}