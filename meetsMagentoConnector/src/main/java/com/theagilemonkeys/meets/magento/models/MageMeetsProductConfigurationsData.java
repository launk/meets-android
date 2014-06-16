package com.theagilemonkeys.meets.magento.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.theagilemonkeys.meets.models.MeetsProduct;
import com.theagilemonkeys.meets.magento.utils.soap.SoapSerializableObject;

import java.util.List;
import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
// This annotation is needed to allow deserialize objects of this class when they don't have
// type info, as when they come from server
@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="class", defaultImpl = MageMeetsProductConfigurationsData.class)
public class MageMeetsProductConfigurationsData extends SoapSerializableObject implements MeetsProduct.ConfigurationsData {
    @JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="class", defaultImpl = MageMeetsProductAttribute.class)
    List<MeetsProduct.Attribute> attributes;

    @JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="class", defaultImpl = MageMeetsProductConfiguration.class)
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
