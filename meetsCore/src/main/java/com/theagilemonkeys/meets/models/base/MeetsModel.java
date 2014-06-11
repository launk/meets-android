package com.theagilemonkeys.meets.models.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theagilemonkeys.meets.ApiMethodModelHelperInterface;
import com.theagilemonkeys.meets.utils.MeetsSerializable;

import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */

public interface MeetsModel<MODEL> extends ApiMethodModelHelperInterface<MODEL>, MeetsSerializable {
    MODEL fetch();
    MODEL fetch(int id);

    MODEL setId(int id);
    int getId();

    /**
     * TODO
     * @param weakAttributes
     * @return
     */
    MODEL include(String... weakAttributes);

    /**
     * Returns true if the model still does not have a valid id.
     */
    @JsonIgnore
    boolean isNew();

    /**
     * Copy all non-null properties from the passed model to this. Listeners are also ignored, so listeners
     * attached to this will be the same after calling this function.
     * @param model The model to copy from
     * @return This model
     */
    MODEL shallowCopyFrom(MODEL model);

    /**
     * Returns a map representation of the model.
     * @param option Option to determine how to construct the Map. By default is {@link com.theagilemonkeys.meets.models.base.MeetsModel.AsMapOption#PUBLIC_GETTERS}
     * @return The map of the model
     */
    Map<String, Object> asMap(AsMapOption option);
    Map<String, Object> asMap();

    enum AsMapOption {
        PUBLIC_GETTERS,
        ALL_FIELDS
    }
}
