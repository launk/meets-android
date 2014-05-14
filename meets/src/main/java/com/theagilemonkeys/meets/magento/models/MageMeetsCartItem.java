package com.theagilemonkeys.meets.magento.models;

import com.google.api.client.util.Key;
import com.theagilemonkeys.meets.ApiMethodModelHelper;
import com.theagilemonkeys.meets.magento.methods.Products;
import com.theagilemonkeys.meets.magento.models.base.MageMeetsModel;
import com.theagilemonkeys.meets.models.MeetsCart;
import com.theagilemonkeys.meets.models.MeetsProduct;
import com.theagilemonkeys.meets.utils.soap.SoapSerializableList;
import com.theagilemonkeys.meets.utils.soap.SoapSerializableMap;

import org.jdeferred.DoneCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class MageMeetsCartItem extends MageMeetsModel<MeetsCart.Item> implements MeetsCart.Item {

    @Key private String item_id;
    @Key private String parent_item_id;
    @Key private String product_type;

    @Key private String product_id;
    @Key private String sku;
    @Key private double qty = 1;
    @Key private String name;
    @Key private String description;
    @Key private double price;
    @Key private SoapSerializableList<SoapSerializableMap<String, String>> super_attribute;

    private MeetsProduct.Configuration configuration;
    private MeetsProduct relatedProduct;

    @Override
    public MeetsCart.Item fillFromProduct(MeetsProduct product) {
        product_id = String.valueOf(product.getId());
        sku = product.getSku();
        name = product.getName();
        description = product.getDescription();
        price = product.getPriceOfSelectedConfiguration();
        product_type = product.getType();
        relatedProduct = product;
        fillSuperAttributes(product);
        return this;
    }

    private void fillSuperAttributes(MeetsProduct product) {
        if("configurable".equals(product_type)) {
            configuration = product.getConfiguration();
            if(configuration == null) return;
            super_attribute = new SoapSerializableList<SoapSerializableMap<String, String>>();
            for(Map.Entry<Integer, Integer> entry : configuration.getAttributeOptionMap().entrySet()) {
                SoapSerializableMap<String, String> option = new SoapSerializableMap<String, String>();
                option.put("key", String.valueOf(entry.getKey()));
                option.put("value", String.valueOf(entry.getValue()));
                super_attribute.add(option);
            }
        }
    }

    @Override
    public MeetsCart.Item setQuantity(double quantity) {
        qty = quantity;
        if (qty < 0) qty = 0;
        return this;
    }

    @Override
    public MeetsCart.Item incQuantity(double quantityIncrement) {
        qty += quantityIncrement;
        if (qty < 0) qty = 0;
        return this;
    }

    @Override
    public double getQuantity() {
        return qty;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getProductId() {
        return product_id == null ? 0 : Integer.parseInt(product_id);
    }

    @Override
    public String getProductSku() {
        return sku;
    }

    @Override
    public String getProductType() {
        return product_type;
    }

    @Override
    public MeetsProduct.Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public int getParentItemId() {
        return parent_item_id == null ? 0 : Integer.parseInt(parent_item_id);
    }

    @Override
    public MeetsCart.Item setProductId(int productId) {
        product_id = String.valueOf(productId);
        return this;
    }

    @Override
    public MeetsProduct getRelatedProduct() {
        return relatedProduct;
    }


    @Override
    public MeetsCart.Item fetchRelatedProduct() {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public List<String> buildUrlExtraSegments() {
                return Arrays.asList(String.valueOf(getProductId()));
            }
        };

        pushMethod(new Products(), params)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        relatedProduct = (MeetsProduct) result;
                    }
                })
                .always(triggerListeners);
        return this;
    }

    @Override
    public MeetsCart.Item fetch() {
        throw new UnsupportedOperationException("Cart items can not be fetched individually. Work with them through MeetsCart");
    }

    @Override
    public MeetsCart.Item setId(int id) {
        item_id = String.valueOf(id);
        return this;
    }

    @Override
    public int getId() {
        return Integer.parseInt(item_id);
    }
}
