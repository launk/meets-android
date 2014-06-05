package com.theagilemonkeys.meets.meetsandroid;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.theagilemonkeys.meets.ApiMethod;
import com.theagilemonkeys.meets.request.RequestWrapper;
import org.jdeferred.Deferred;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class SpiceRequestWrapper<RESULT> implements RequestWrapper<RESULT> {

    private ApiMethod<RESULT> method;
    private SpiceManager spiceManager;

    public SpiceRequestWrapper(SpiceManager spiceManager, ApiMethod<RESULT> method) {
        this.method = method;
        this.spiceManager = spiceManager;
    }

    @Override
    public void makeRequest(Deferred deferred) {
        makeRequest(deferred, null);
    }

    @Override
    public void makeRequest(Deferred deferred, String cacheKey) {
        MeetsSpiceRequest request = new MeetsSpiceRequest(method.getResponseClass());
        MeetsRequestListener listener = new MeetsRequestListener(deferred);

        if (cacheKey != null && method.getCacheDuration() >= 0){
            if (method.isAlwaysGetFromCacheFirst())
                spiceManager.getFromCacheAndLoadFromNetworkIfExpired(request, cacheKey, method.getCacheDuration(), listener);
            else
                spiceManager.execute(request, cacheKey, method.getCacheDuration(), listener);
        }
        else {
            spiceManager.execute(request, listener);
        }
    }

    private class MeetsSpiceRequest extends SpiceRequest<RESULT> {
        public MeetsSpiceRequest(Class<RESULT> clazz) {
            super(clazz);
        }

        @Override
        public RESULT loadDataFromNetwork() throws Exception {
            return method.loadDataFromNetwork();
        }
    }

    @SuppressWarnings("unchecked")
    private class MeetsRequestListener implements RequestListener<RESULT> {
        private Deferred deferred;

        public MeetsRequestListener(Deferred deferred) {
            this.deferred = deferred;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if(deferred.isPending()) {
                deferred.reject(spiceException);
            }
        }

        @Override
        public void onRequestSuccess(RESULT result) {
            if(deferred.isPending()) {
                deferred.resolve(result);
            }
        }
    };
}
