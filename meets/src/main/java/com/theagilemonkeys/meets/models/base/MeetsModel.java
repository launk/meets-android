package com.theagilemonkeys.meets.models.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.theagilemonkeys.meets.ApiMethodModelHelperInterface;

import java.io.Serializable;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="class")
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
public interface MeetsModel<MODEL> extends ApiMethodModelHelperInterface<MODEL>, Serializable {
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
    boolean isNew();

    /**
     * Copy all non-null properties from the passed model to this. Listeners are also ignored, so listeners
     * attached to this will be the same after calling this function.
     * @param model The model to copy from
     * @return This model
     */
     MODEL shallowCopyFrom(MODEL model);
}
