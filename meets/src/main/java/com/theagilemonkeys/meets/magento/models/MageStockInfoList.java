package com.theagilemonkeys.meets.magento.models;

import com.theagilemonkeys.meets.ApiMethodModelHelper;
import com.theagilemonkeys.meets.magento.methods.CatalogInventoryStockItemList;
import com.theagilemonkeys.meets.magento.models.base.MageMeetsModel;
import com.theagilemonkeys.meets.models.MeetsStock;
import com.theagilemonkeys.meets.utils.soap.SoapSerializableList;

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
public class MageStockInfoList extends MageMeetsModel<MeetsStock.ItemList> implements MeetsStock.ItemList {
    private SoapSerializableList<Integer> idsList = new SoapSerializableList<Integer>();
    private List<MeetsStock.Item> stockInfoList;


    @Override
    public MeetsStock.ItemList fetch(List<Integer> ids) {
        setIds(ids).fetch();
        return this;
    }


    @Override
    public MeetsStock.ItemList fetch() {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("products", idsList);
                return params;
            }
        };
        pushMethod(new CatalogInventoryStockItemList(), params)
                .done(updateFromResult)
                .always(triggerListeners);
        return this;
    }

    @Override
    public MeetsStock.ItemList setId(int id) {
        idsList = new SoapSerializableList<Integer>();
        idsList.add(id);
        return this;
    }

    @Override
    public MeetsStock.ItemList setIds(List<Integer> ids) {
        idsList = new SoapSerializableList<Integer>();
        idsList.addAll(ids);
        return this;
    }

    @Override
    public List<Integer> getIds() {
        return idsList;
    }

    @Override
    public List<MeetsStock.Item> getList() {
        return new ArrayList<MeetsStock.Item>(stockInfoList);
    }

    @Override
    public int getId() {
        return idsList.get(0);
    }

    @Override
    protected void updateFromResult(Object fetchedResult) {
        stockInfoList = (List<MeetsStock.Item>) fetchedResult;
    }
}
