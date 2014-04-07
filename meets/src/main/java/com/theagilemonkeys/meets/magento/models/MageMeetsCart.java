package com.theagilemonkeys.meets.magento.models;

import com.google.api.client.util.Key;
import com.theagilemonkeys.meets.ApiMethodModelHelper;
import com.theagilemonkeys.meets.magento.MageApiMethodCollectionResponseClasses;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartCreate;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartCustomerAddresses;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartCustomerSet;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartInfo;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartOrder;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartPaymentList;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartPaymentMethod;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartProductAdd;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartProductRemove;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartShippingList;
import com.theagilemonkeys.meets.magento.methods.ShoppingCartShippingMethod;
import com.theagilemonkeys.meets.magento.models.base.MageMeetsModel;
import com.theagilemonkeys.meets.models.MeetsAddress;
import com.theagilemonkeys.meets.models.MeetsCart;
import com.theagilemonkeys.meets.models.MeetsCustomer;
import com.theagilemonkeys.meets.models.MeetsProduct;
import com.theagilemonkeys.meets.models.base.MeetsFactory;
import com.theagilemonkeys.meets.utils.soap.Serializable;
import com.theagilemonkeys.meets.utils.soap.SoapParser;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class MageMeetsCart extends MageMeetsModel<MeetsCart> implements MeetsCart {

    @Key private int quote_id;
    @Key private double items_count = 0;
    @Key String checkout_method;
    @Key String customer_id;
    @Key private double items_qty = 0;
    @Key private double subtotal = 0;
    @Key private double grand_total = 0;
    @Key
    @SoapParser.ListInfo(MageApiMethodCollectionResponseClasses.CartItems.class)
    private List<Item> items;

    private List<Shipping> shippingMethods;
    private List<Payment> paymentMethods;
    private String lastOrderId;
    private MeetsCustomer attachedCustomer;
    private MeetsAddress attachedBillingAddress;
    private MeetsAddress attachedShippingAddress;
    private Shipping attachedShippingMethod;
    private Payment attachedPaymentMethod;

    {
        //Disable cache for the entire model operations
        setModelCache(false);
    }


    @Override
    public MeetsCart fetch() {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("quoteId", quote_id);
                return params;
            }
        };

        pushMethod(new ShoppingCartInfo(), params)
                .done(updateFromResult)
                .always(triggerListeners);
        return this;
    }

    private void resetFields() {
        quote_id = 0;
        items_count = 0;
        checkout_method = null;
        customer_id = null;
        items_qty = 0;
        subtotal = 0;
        grand_total = 0;

        items = null;
        shippingMethods = null;
        paymentMethods = null;
        lastOrderId = null;
        attachedCustomer = null;
        attachedBillingAddress = null;
        attachedShippingAddress = null;
        attachedShippingMethod = null;
        attachedPaymentMethod = null;
    }

    @Override
    public MeetsCart setId(int id) {
        quote_id = id;
        return this;
    }

    @Override
    public int getId() {
        return quote_id;
    }

    @Override
    public MeetsCart create() {
        pushMethod(new ShoppingCartCreate())
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        resetFields();
                        updateFromResult(result);
                    }
                })
                .always(triggerListeners);
        nextWaitForPrevious();
        return this;
    }

    @Override
    public MeetsCart attachCustomer(final MeetsCustomer customer) {
        ((MageMeetsCustomer) customer).setMode(MageMeetsCustomer.MODE_CUSTOMER);
        return internalAttachCustomer(customer);
    }

    @Override
    public MeetsCart attachCustomerAsGuest(MeetsCustomer customer) {
        ((MageMeetsCustomer) customer).setMode(MageMeetsCustomer.MODE_GUEST);
        return internalAttachCustomer(customer);
    }

    private MeetsCart internalAttachCustomer(final MeetsCustomer customer) {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("quoteId", quote_id);
                params.put("customer", customer);
                return params;
            }
        };
        attachedCustomer = customer;
        pushMethod(new ShoppingCartCustomerSet(), params).always(triggerListeners);
        return this;
    }

    @Override
    public MeetsCart attachAddresses(final MeetsAddress billingAddress, final MeetsAddress shippingAddress) {
        ((MageMeetsAddress) billingAddress).setMode(MageMeetsAddress.MODE_BILLING);
        ((MageMeetsAddress) shippingAddress).setMode(MageMeetsAddress.MODE_SHIPPING);

        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                Serializable.List<MageMeetsAddress> addresses = new Serializable.List<MageMeetsAddress>();
                addresses.add((MageMeetsAddress) billingAddress);
                addresses.add((MageMeetsAddress) shippingAddress);

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("quoteId", quote_id);
                params.put("customer", addresses);
                return params;
            }
        };
        attachedBillingAddress = billingAddress;
        attachedShippingAddress = shippingAddress;
        pushMethod(new ShoppingCartCustomerAddresses(), params).always(triggerListeners);
        return this;
    }

    @Override
    public MeetsCart attachShippingMethod(final Shipping shipping) {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("quoteId", quote_id);
                params.put("method", shipping.getCode());
                return params;
            }
        };
        attachedShippingMethod = shipping;
        pushMethod(new ShoppingCartShippingMethod(), params).always(triggerListeners);
        return this;
    }

    @Override
    public MeetsCart attachPaymentMethod(final Payment paymentMethod) {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("quoteId", quote_id);
                params.put("method",paymentMethod);
                return params;
            }
        };
        attachedPaymentMethod = paymentMethod;
        pushMethod(new ShoppingCartPaymentMethod(), params).always(triggerListeners);
        return this;
    }

    @Override
    public MeetsCart order() {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("quoteId", quote_id);
                return params;
            }
        };

        pushMethod(new ShoppingCartOrder(), params)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        lastOrderId = (String) result;
                    }
                })
                .always(triggerListeners);
        return this;
    }

    @Override
    public MeetsCart addItem(final MeetsProduct product, final double quantity) {
        return addItems(Arrays.asList(MeetsFactory.get().makeCartItem(product).setQuantity(quantity)));
    }

    @Override
    public MeetsCart addItem(Item item) {
        return addItems(Arrays.asList(item));
    }

    @Override
    public MeetsCart addItems(List<MeetsProduct> products, List<Double> quantities) {
        List<Item> items = new ArrayList<Item>();
        int productsLength = products.size();
        int quantitiesLength = quantities.size();
        for(int i = 0; i < productsLength; ++i) {
            MeetsProduct product = products.get(i);
            double quantity = quantities.get(i % quantitiesLength);
            items.add(MeetsFactory.get().makeCartItem(product).setQuantity(quantity));
        }
        return addItems(items);
    }

    @Override
    public MeetsCart addItems(final List<Item> items) {
        localAddItems(items);

        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                // Create a list with the product we want to add to cart
                Serializable.List<Item> cartItemsToSend = new Serializable.List<Item>();
                cartItemsToSend.addAll(items);
                // Create the params and call the method
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("quoteId", quote_id);
                params.put("products", cartItemsToSend);
                return params;
            }
        };

        pushMethod(new ShoppingCartProductAdd(), params)
                .fail(new FailCallback() {
                    @Override
                    public void onFail(Object result) {
                        localRemoveItems(items);
                    }
                })
                .always(triggerListeners);
        return this;
    }

    private void localAddItems(List<Item> items) {
        for(Item item : items) localAddItem(item);
    }

    private void localAddItem(Item item) {
        if (items == null)
            items = new ArrayList<Item>();
        // Update the local data of cart products
        Item localCartItem = null;
        double qty = item.getQuantity();

        for(Item localItem : items){
            if( productsAreTheSame(localItem, item) ){
                localItem.incQuantity(qty);
                localCartItem = localItem;
                break;
            }
        }
        if ( localCartItem == null){
            localCartItem = item;
            items.add(localCartItem);
            ++items_count;
        }

        grand_total += localCartItem.getPrice() * qty;
        subtotal += localCartItem.getPrice() * qty;
        items_qty +=  qty;
    }

    private boolean productsAreTheSame(Item itemA, Item itemB) {
        boolean areTheSame = ( itemA.getProductId() == itemB.getProductId() );
        if (areTheSame) {
            MeetsProduct.Configuration configA = itemA.getConfiguration();
            MeetsProduct.Configuration configB = itemB.getConfiguration();

            // If items have the related product and both are configurables, we do an extra check to
            // ensure they have the same configuration.
            if ( ("configurable".equals(itemA.getProductType()) || "configurable".equals(itemB.getProductType())) &&
                 configA != null && configB != null) {
                areTheSame = configA.getAttributeOptionMap().equals(configB.getAttributeOptionMap());
            }
        }
        return areTheSame;
    }


    @Override
    public MeetsCart removeItem(MeetsProduct product, double quantity) {
        return removeItems(Arrays.asList(MeetsFactory.get().makeCartItem(product).setQuantity(quantity)));
    }

    @Override
    public MeetsCart removeItem(Item item) {
        return removeItems(Arrays.asList(item));
    }

    @Override
    public MeetsCart removeItems(List<MeetsProduct> products, List<Double> quantities) {
        List<Item> items = new ArrayList<Item>();
        int productsLength = products.size();
        int quantitiesLength = quantities.size();
        for(int i = 0; i < productsLength; ++i) {
            MeetsProduct product = products.get(i);
            double quantity = quantities.get(i % quantitiesLength);
            items.add(MeetsFactory.get().makeCartItem(product).setQuantity(quantity));
        }
        return removeItems(items);
    }

    @Override
    public MeetsCart removeItems(List<Item> items) {
        final List<Item> removedItems = localRemoveItems(items);

        if( ! removedItems.isEmpty() ){
            ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
                @Override
                public Map<String, Object> buildParams() {
                    // Create a list with the product we want to add to cart
                    Serializable.List<Item> cartItemsToSend = new Serializable.List<Item>();
                    cartItemsToSend.addAll(removedItems);
                    // Create the params and call the method
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("quoteId", quote_id);
                    params.put("products", cartItemsToSend);
                    return params;
                }
            };

            pushMethod(new ShoppingCartProductRemove(), params)
                    .fail(new FailCallback() {
                        @Override
                        public void onFail(Object result) {
                            localAddItems(removedItems);
                        }
                    })
                    .always(triggerListeners);
        }
        else {
            triggerListeners();
        }
        return this;
    }

    private List<Item> localRemoveItems(List<Item> items) {
        List<Item> removedItems = new ArrayList<Item>();
        for(Item item : items) {
            Item removedItem = localRemoveItem(item);
            if (removedItem != null) removedItems.add(item);
        }
        return removedItems;
    }

    private Item localRemoveItem(Item itemToRemove) {
        if(items == null)
            return null;

        Item localCartItem = null;

        for(Item item : items){
            if( productsAreTheSame(item, itemToRemove) ){
                localCartItem = item;
                break;
            }
        }
        if(localCartItem == null) return null;

        double realQty = Math.min(localCartItem.getQuantity(), itemToRemove.getQuantity());
        localCartItem.incQuantity(-realQty);
        if (localCartItem.getQuantity() <= 0){
            items.remove(localCartItem);
            --items_count;
        }

        grand_total -= localCartItem.getPrice() * realQty;
        subtotal -= localCartItem.getPrice() * realQty;
        items_qty -= realQty;

        return localCartItem;
    }

    @Override
    public List<Item> getItems() {
        return items;
    }

    @Override
    public double getItemsTotalQuantity() {
        return items_qty;
    }

    @Override
    public double getSubtotal() {
        return subtotal;
    }

    @Override
    public double getTotal() {
        return grand_total;
    }

    @Override
    public String getOrderId() {
        return lastOrderId;
    }

    @Override
    public MeetsCustomer getAttachedCustomer() {
        return attachedCustomer;
    }

    @Override
    public MeetsAddress getAttachedBillingAddress() {
        return attachedBillingAddress;
    }

    @Override
    public MeetsAddress getAttachedShippingAddress() {
        return attachedShippingAddress;
    }

    @Override
    public Shipping getAttachedShippingMethod() {
        return attachedShippingMethod;
    }

    @Override
    public Payment getAttachedPaymentMethod() {
        return attachedPaymentMethod;
    }

    @Override
    public List<Shipping> getShippingMethods() {
        return shippingMethods;
    }

    @Override
    public List<Payment> getPaymentMethods() {
        return paymentMethods;
    }

    @Override
    public MeetsCart fetchShippingMethods() {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("quoteId", quote_id);
                return params;
            }
        };

        forceNextCacheToBe(true);
        pushMethod(new ShoppingCartShippingList(), params)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        shippingMethods = (List<Shipping>) result;
                    }
                })
                .always(triggerListeners);
        return this;
    }

    @Override
    public MeetsCart fetchPaymentMethods() {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("quoteId", quote_id);
                return params;
            }
        };

        forceNextCacheToBe(true);
        pushMethod(new ShoppingCartPaymentList(), params)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        paymentMethods = (List<Payment>) result;
                    }
                })
                .always(triggerListeners);
        return this;
    }
}
