package com.theagilemonkeys.meets.magento.models.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theagilemonkeys.meets.ApiMethod;
import com.theagilemonkeys.meets.ApiMethodModelHelper;
import com.theagilemonkeys.meets.models.base.MeetsListener;
import com.theagilemonkeys.meets.models.base.MeetsModel;
import com.theagilemonkeys.meets.utils.Copier;
import com.theagilemonkeys.meets.utils.MeetsSerializable;
import com.theagilemonkeys.meets.utils.soap.SoapSerializableObject;
import org.jdeferred.*;

import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
@SuppressWarnings("unchecked")
public abstract class MageMeetsModel<MODEL extends MeetsModel> extends SoapSerializableObject implements MeetsModel<MODEL> {
    private transient ApiMethodModelHelper<MODEL> apiMethodCtrl = new ApiMethodModelHelper<MODEL>((MODEL) this);

    @Override
    public boolean isNew() {
        return getId() <= 0;
    }

    @Override
    public MODEL fetch(int id) {
        return (MODEL) setId(id).fetch();
    }

    /**
     * Update this from the object returned after fetch() is called. Copy all properties from fecthedResult
     * to this, using the Copier instance returned by getCopier()
     *
     * @param fetchedResult
     */
    protected void updateFromResult(Object fetchedResult) {
        getCopier().copyProperties(this, fetchedResult);
    }

    protected transient AlwaysCallback triggerListeners = new AlwaysCallback() {
        @Override
        public void onAlways(Promise.State state, Object resolved, Object rejected) {
            triggerListeners((Exception) rejected);
        }
    };

    protected transient DoneCallback updateFromResult = new DoneCallback() {
        @Override
        public void onDone(Object result) {
            updateFromResult(result);
        }
    };

    /**
     * Return the Copier instance used to copy properties from fetched object to this.
     * Child classes can override this method to configure the copier to, for example,
     * avoid cloning certain properties. In this case, child classes must call super and work
     * with the copier returned.
     *
     * @return Copier
     */
    protected Copier getCopier() {
        return new Copier().setIgnoreNulls(true)
                .ignoreInstancesOf(ApiMethodModelHelper.class);
    }

    @Override
    public MODEL shallowCopyFrom(MODEL model) {
        getCopier().copyProperties(this, model);
        return (MODEL) this;
    }

    @Override
    public MODEL include(String... weakAttributes) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap(AsMapOption.PUBLIC_GETTERS);
    }

    @Override
    public Map<String, Object> asMap(AsMapOption option) {
        ObjectMapper mapper = new ObjectMapper();

        Class mixinClass = AsMapIncludePublicGetters.class;
        if (option == AsMapOption.ALL_FIELDS)
            mixinClass = AsMapIncludeAllFields.class;

        mapper.addMixInAnnotations(MeetsSerializable.class, mixinClass);
        return mapper.convertValue(this, Map.class);
    }

    @JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY,
        isGetterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY,
        setterVisibility = JsonAutoDetect.Visibility.NONE
    )
    private interface AsMapIncludePublicGetters {}

    @JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
    )
    private interface AsMapIncludeAllFields {}


    /////////////////////////////////////////////////////////////////////////
    //////////////////// ApiMethodModelHelper delegation /////////////////////
    /////////////////////////////////////////////////////////////////////////


    public Promise pushMethod(ApiMethod method) {
        return apiMethodCtrl.pushMethod(method);
    }

    public MODEL forceNextCacheToBe(boolean enable) {
        return apiMethodCtrl.forceNextCacheToBe(enable);
    }

    public Promise pushPipe(DonePipe donePipe, FailPipe failPipe) {
        return apiMethodCtrl.pushPipe(donePipe, failPipe);
    }

    public void triggerListeners(Exception e) {
        apiMethodCtrl.triggerListeners(e);
    }

    public Promise pushMethod(ApiMethod method, ApiMethodModelHelper.DelayedParams params) {
        return apiMethodCtrl.pushMethod(method, params);
    }

    public Promise pushDeferred(Deferred deferred) {
        return apiMethodCtrl.pushDeferred(deferred);
    }

    public MODEL setModelCache(boolean enable) {
        return apiMethodCtrl.setModelCache(enable);
    }

    public void triggerListeners() {
        apiMethodCtrl.triggerListeners();
    }

    @Override
    public void ignoreGlobalListener() {
        apiMethodCtrl.ignoreGlobalListener();
    }

    @Override
    public MODEL await(MeetsListener<MODEL> listener) {
        return apiMethodCtrl.await(listener);
    }

    @Override
    public MODEL await(MeetsListener<MODEL> listener, boolean keepListening) {
        return apiMethodCtrl.await(listener, keepListening);
    }

    @Override
    public MODEL noLongerWait(MeetsListener<MODEL> listener) {
        return apiMethodCtrl.noLongerWait(listener);
    }

    @Override
    public MODEL allNoLongerWait() {
        return apiMethodCtrl.allNoLongerWait();
    }

    @Override
    public MODEL nextWaitForPrevious() {
        return apiMethodCtrl.nextWaitForPrevious();
    }
}
