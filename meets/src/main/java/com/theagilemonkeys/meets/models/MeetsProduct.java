package com.theagilemonkeys.meets.models;

import com.theagilemonkeys.meets.models.base.MeetsModel;
import com.theagilemonkeys.meets.utils.MeetsSerializable;

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
    double getPriceOfConfiguration(Configuration config);
    double getPriceOfSelectedConfiguration();
    String getImageUrl();
    List<String> getImages();
    List<MeetsProduct> getAssociatedProducts();

    MeetsProduct fetchImages();
    MeetsProduct fetchConfigurationsData();
    ConfigurationsData getConfigurationData();
    MeetsProduct setConfiguration(Configuration configuration);
    Configuration getConfiguration();

    public interface ConfigurationsData extends MeetsSerializable {
        List<Attribute> getAttributes();
        Attribute getAttribute(int attributeId);
        Attribute.Option getOption(int attributeId, int optionId);
        List<Configuration> getConfigurations();
        double calculateTotalPrice(double basePrice, Configuration configuration);
    }

    public interface Configuration extends MeetsSerializable {
        Map<Integer, Integer> getAttributeOptionMap();
        int getStock();
    }

    public interface Attribute extends MeetsSerializable {
        int getId();
        String getLabel();
        List<Option> getOptions();

        public interface Option extends MeetsSerializable {
            int getId();
            String getLabel();
            double getPriceOffset();
            boolean isPercentageOffset();
        }
    }
}
