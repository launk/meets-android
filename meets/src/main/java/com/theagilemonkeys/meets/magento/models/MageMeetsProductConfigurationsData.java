package com.theagilemonkeys.meets.magento.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.api.client.util.Key;
import com.theagilemonkeys.meets.models.MeetsProduct;
import com.theagilemonkeys.meets.utils.soap.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class MageMeetsProductConfigurationsData extends Serializable.Object implements MeetsProduct.ConfigurationsData {
    @Key
    @JsonDeserialize(as = ArrayList.class, contentAs = MageMeetsProductAttribute.class)
    List<MeetsProduct.Attribute> attributes;
    @Key
    @JsonDeserialize(as = ArrayList.class, contentAs = MageMeetsProductConfiguration.class)
    List<MeetsProduct.Configuration> products;

    @Override
    public List<MeetsProduct.Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public MeetsProduct.Attribute getAttribute(int attributeId) {
        for(MeetsProduct.Attribute attr : attributes) {
            if(attr.getId() == attributeId)
                return attr;
        }
        return null;
    }


    @Override
    public MeetsProduct.Attribute.Option getOption(int attributeId, int optionId) {
        MeetsProduct.Attribute attr = getAttribute(attributeId);
        if (attr != null) {
            for (MeetsProduct.Attribute.Option opt : attr.getOptions()) {
                if (opt.getId() == optionId)
                    return opt;
            }
        }
        return null;
    }

    @Override
    public List<MeetsProduct.Configuration> getConfigurations() {
        return products;
    }

    @Override
    public double calculateTotalPrice(double basePrice, MeetsProduct.Configuration configuration) {
        double totalOffset = 0;
        for(Map.Entry<Integer, Integer> entry : configuration.getAttributeOptionMap().entrySet()) {
            MeetsProduct.Attribute.Option opt = getOption(entry.getKey(), entry.getValue());
            double offset = opt.getPriceOffset();
            if (opt.isPercentageOffset())
                offset = (offset/100) * basePrice;
            totalOffset += offset;
        }
        return basePrice + totalOffset;
    }
}
