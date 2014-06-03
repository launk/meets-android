package com.theagilemonkeys.meets.request.robospice;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.KeySanitationExcepion;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.persistence.keysanitation.DefaultKeySanitizer;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.theagilemonkeys.meets.ApiMethod;
import com.theagilemonkeys.meets.Meets;
import com.theagilemonkeys.meets.MeetsDefaultConfig;
import com.theagilemonkeys.meets.utils.StringUtils;
import org.jdeferred.Deferred;
import org.jdeferred.impl.DeferredObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class SpiceRequestWrapper<RESULT> extends SpiceRequest<RESULT> implements RequestListener<RESULT> {

    private MeetsRobospiceConfig config;
    private ApiMethod<RESULT> method;

    public SpiceRequestWrapper(MeetsRobospiceConfig config, ApiMethod<RESULT> method){
        super(method.getResponseClass());
        this.config = config;
        this.method = method;
    }

    private void makeRequest() {
        makeRequest(null);
    }

    private void makeRequest(String cacheKey) {
        if (cacheKey != null && method.getCacheDuration() >= 0){
            if (method.isAlwaysGetFromCacheFirst())
                config.getSpiceManager().getFromCacheAndLoadFromNetworkIfExpired(this, cacheKey, method.getCacheDuration(), this);
            else
                config.getSpiceManager().execute(this, cacheKey, method.getCacheDuration(), this);
        }
        else {
            config.getSpiceManager().execute(this, this);
        }
    }

    @Override
    public RESULT loadDataFromNetwork() throws Exception {
        return method.loadDataFromNetwork();
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        if(runDeferred.isPending()) {
            runDeferred.reject(e);
        }
    }

    @Override
    public void onRequestSuccess(RESULT response) {
        if(runDeferred.isPending()) {
            runDeferred.resolve(response);
        }
    }

}
