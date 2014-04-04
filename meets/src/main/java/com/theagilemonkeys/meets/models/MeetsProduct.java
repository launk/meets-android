package com.theagilemonkeys.meets.models;

import com.theagilemonkeys.meets.models.base.MeetsModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public interface MeetsProduct extends MeetsModel<MeetsProduct> {
    String getType();
    String getSku();
    String getName();
    String getDescription();
    double getPrice();
    String getImageUrl();
    List<String> getImages();
    List<MeetsProduct> getAssociatedProducts();

    MeetsProduct fetchImages();
    MeetsProduct fetchConfigurationsData();
    ConfigurationsData getConfigurationData();
    MeetsProduct setConfiguration(Configuration configuration);
    Configuration getConfiguration();

    public interface ConfigurationsData extends Serializable {
        List<Attribute> getAttributes();
        Attribute getAttribute(int attributeId);
        Attribute.Option getOption(int attributeId, int optionId);
        List<Configuration> getConfigurations();
        double calculateTotalPrice(double basePrice, Configuration configuration);
    }

    public interface Configuration extends Serializable{
        Map<Integer, Integer> getAttributeOptionMap();
        int getStock();
    }

    public interface Attribute extends Serializable {
        int getId();
        String getLabel();
        List<Option> getOptions();

        public interface Option extends Serializable {
            int getId();
            String getLabel();
            double getPriceOffset();
            boolean isPercentageOffset();
        }
    }
}
