package com.theagilemonkeys.meets.magento.models;

import com.google.api.client.util.Key;
import com.theagilemonkeys.meets.ApiMethodCollectionResponseClasses;
import com.theagilemonkeys.meets.ApiMethodModelHelper;
import com.theagilemonkeys.meets.magento.methods.CatalogProductInfo;
import com.theagilemonkeys.meets.magento.methods.CatalogProductList;
import com.theagilemonkeys.meets.magento.methods.Products;
import com.theagilemonkeys.meets.magento.models.base.MageMeetsModel;
import com.theagilemonkeys.meets.models.MeetsProduct;
import com.theagilemonkeys.meets.utils.StringUtils;
import com.theagilemonkeys.meets.utils.soap.Serializable;

import org.jdeferred.DoneCallback;

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
public class MageMeetsProduct extends MageMeetsModel<MeetsProduct> implements MeetsProduct {
    @Key private int entity_id;
    @Key private String product_id; //This is the same as entity_id, but some Soap calls resturns it with different names
    @Key private String sku;
    @Key private String type_id;
    @Key private String name;
    @Key private String description;
    @Key private String short_description;
    @Key private String image_url;
    @Key private Double final_price_with_tax;
    @Key private String price;
    @Key private ApiMethodCollectionResponseClasses.ListOfGenericMaps additional_attributes;

    private Map<String, String> parsedAdditionalAttributes;
    private List<MeetsProduct> associatedProducs;
    private List<String> images;

    @Override
    public MeetsProduct fetch() {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public List<String> buildUrlExtraSegments() {
                return Arrays.asList(String.valueOf(getId()));
            }
        };

        pushMethod(new Products(), params)
                .done(updateFromResult)
                .always(triggerListeners);
        return this;
    }

    ////////////////////// Property getters and setters////////////////////////

    @Override
    public int getId() {
        return entity_id > 0 ? entity_id : Integer.parseInt(product_id);
    }

    @Override
    public MeetsProduct setId(int id) {
        entity_id = id;
        product_id = String.valueOf(id);
        return this;
    }

    @Override
    public String getSku() {
        return sku;
    }

    @Override
    public String getType() {
        return type_id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        String res = StringUtils.safeValueOf(description);
        if ( res.length() <= 0 )
            res = StringUtils.safeValueOf(short_description);

        return res;
    }

    @Override
    public String getImageUrl() {
        return image_url;
    }

    @Override
    public List<String> getImages() {
        return images;
    }

    @Override
    public double getPrice() {
        return final_price_with_tax != null ? final_price_with_tax
                                            : Double.parseDouble(price);
    }

    @Override
    public List<MeetsProduct> getAssociatedProducts() {
        return associatedProducs;
    }

    @Override
    public MeetsProduct fetchImages() {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public List<String> buildUrlExtraSegments() {
                return Arrays.asList(String.valueOf(getId()), "images");
            }
        };

        pushMethod(new Products().setResponseClass(null), params)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
                        images = new ArrayList<String>();
                        for(Map<String, Object> imageData : (List<Map>)result) {
                            images.add((String) imageData.get("url"));
                        }
                    }
                })
                .always(triggerListeners);
        return this;
    }

    @Override
    public MeetsProduct fetchWithAdditionalAttributes(String... additionalAttributes) {
        return fetchWithAdditionalAttributes(Arrays.asList(additionalAttributes));
    }

    @Override
    public MeetsProduct fetchWithAdditionalAttributes(final List<String> additionalAttributes) {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                Serializable.List serializableAdditionalAttrs = new Serializable.List(additionalAttributes);

                Map<String, Object> attributes = new Serializable.Map<String, Object>();
                attributes.put("additional_attributes", serializableAdditionalAttrs);

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("product", getId());
                params.put("attributes", attributes);
                return params;
            }
        };

        pushMethod(new CatalogProductInfo(), params)
                .done(new DoneCallback() {
                    @Override
                    public void onDone(Object result) {
//                        debería tener ya aquí los valores de los attributos
                    }
                })
                .always(triggerListeners);
        return this;
    }

    @Override
    public Map<String, String> getAdditionalAttributes() {
        throw new UnsupportedOperationException("Not implemented yet");
//        return parsedAdditionalAttributes;
    }

    @Override
    public MeetsProduct fetchAssociatedProducts() {
        ApiMethodModelHelper.DelayedParams params = new ApiMethodModelHelper.DelayedParams() {
            @Override
            public Map<String, Object> buildParams() {
                List<Map<String, Object>> complexFilter = new Serializable.List<Map<String, Object>>();
                Map<String, Object> filter = new Serializable.Map<String, Object>();
                Map<String, Object> filterValue = new Serializable.Map<String, Object>();
                filterValue.put("key", "like");
                filterValue.put("value", sku + "-%");
                filter.put("key", "sku");
                filter.put("value", filterValue);
                complexFilter.add(filter);

                Map<String, Object> filters = new Serializable.Map<String, Object>();
                filters.put("complex_filter", complexFilter);

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("filters",filters);
                return params;
            }
        };

        pushMethod(new CatalogProductList(), params).done(new DoneCallback() {
            @Override
            public void onDone(Object result) {
                associatedProducs = (List<MeetsProduct>) result;
                //Images are not returned by this call, so get the parent one
                for (MeetsProduct product : associatedProducs) {
                    ((MageMeetsProduct) product).image_url = image_url;
                }
            }
        })
        .always(triggerListeners);

        return this;
    }
}
