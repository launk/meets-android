package com.theagilemonkeys.meets.magento.models;

import com.theagilemonkeys.meets.models.MeetsStock;
import com.theagilemonkeys.meets.utils.soap.SoapSerializableObject;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */

public class MageStockItem extends SoapSerializableObject implements MeetsStock.Item {
    private String product_id;
    private String sku;
    private String qty;
    private String is_in_stock;

    @Override
    public int getProductId() {
        return Integer.parseInt(product_id);
    }

    @Override
    public String getProductSku() {
        return sku;
    }

    @Override
    public boolean isInStock() {
        return "1".equals(is_in_stock);
    }

    @Override
    public double getQuantity() {
        return Double.parseDouble(qty);
    }
}
