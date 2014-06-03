package com.theagilemonkeys.meets;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.KeySanitationExcepion;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.persistence.keysanitation.DefaultKeySanitizer;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.theagilemonkeys.meets.utils.StringUtils;

import org.jdeferred.Deferred;
import org.jdeferred.impl.DeferredObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public abstract class ApiMethod<RESULT> {

    protected volatile long cacheDuration = DurationInMillis.ONE_HOUR;
    protected volatile boolean alwaysGetFromCacheFirst = true;

    protected List<String> urlExtraSegments;
    protected Map <String, Object> params;
    protected Class responseClass;
    protected Deferred runDeferred;
    protected MeetsDefaultConfig config;

    public ApiMethod(Class<RESULT> responseClass) {
        this.responseClass = responseClass;
        config = Meets.getConfig();
    }

    public ApiMethod<RESULT> setResponseClass(Class responseClass) {
        this.responseClass = responseClass;
        return this;
    }

    public Class<RESULT> getResponseClass() {
        return responseClass;
    }

    /**
     * Set the cache duration for this method. This method will override default cache duration
     * for this method.
     * @param milliseconds The cache duration in milliseconds. If < 0, no cache will be used. If == 0,
     *                     the cache never expires. You will prefer use DurationInMillis constants.
     * @return This method
     */
    public ApiMethod<RESULT> setCacheDuration(long milliseconds) {
        cacheDuration = milliseconds;
        return this;
    }

    /**
     * If you pass true to this method and call "run", it will try to get the data from cache and,
     * regardless of the result, the request is always performed to update cache data (and return it
     * if it was not found before).
     * Note that if data in cache is found and is expired, the response listener will be called twice:
     * once with the dirty data from cache and another with the updated data when the request finish.
     * @param val
     * @return
     */
    public ApiMethod<RESULT> setAlwaysGetFromCacheFirst(boolean val) {
        alwaysGetFromCacheFirst = val;
        return this;
    }

    public long getCacheDuration() {
        return cacheDuration;
    }
    public boolean isAlwaysGetFromCacheFirst() {
        return alwaysGetFromCacheFirst;
    }

    public Deferred run(String... urlExtraSegments) {
        return run(null, urlExtraSegments);
    }

    public Deferred run(Map<String, Object> params, String... urlExtraSegments){
        return run(params, Arrays.asList(urlExtraSegments));
    }

    public Deferred run(Map<String, Object> params, List<String> urlExtraSegments){
        runDeferred = new DeferredObject();

        prepareParams(params, urlExtraSegments);
        makeRequest();

        return runDeferred;
    }

    public RESULT syncRun(Map<String, Object> params, List<String> urlExtraSegments) throws Exception {
        prepareParams(params, urlExtraSegments);
        return loadDataFromNetwork();
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

    private void prepareParams(Map<String, Object> params, List<String> urlExtraSegments) {
        this.params = new TreeMap(String.CASE_INSENSITIVE_ORDER);

        this.params.put("store", config.getStoreId());
        this.params.put("storeId", config.getStoreId());
        this.params.put("storeView", config.getStoreId());

        if (params != null)
            this.params.putAll(params);

        this.urlExtraSegments = new ArrayList<String>();
        if (urlExtraSegments != null)
            this.urlExtraSegments.addAll(urlExtraSegments);
    }

    public String getCacheKey(){
        String key = null;
        try {
            key = (String) new DefaultKeySanitizer().sanitizeKey(generateUrl());
        } catch (KeySanitationExcepion e) {
            e.printStackTrace();
        }
        return key;
    }

    protected abstract String getBaseUrl();

    protected String generateUrl(){
        return getBaseUrl() + getMethodName() + "/" + generateUrlExtraSegments() + "?" + generateUrlParams();
    }

    protected String getMethodName() {
        return StringUtils.toLowerCaseFirst(getClass().getSimpleName());
    }
    protected String generateUrlExtraSegments(){
        String res = "";
        if ( urlExtraSegments != null ){
            for ( String segment : urlExtraSegments ){
                res += segment + "/";
            }
        }
        return res;
    }

    protected String generateUrlParams(){
        String res = "";
        if ( params != null){
            for (Map.Entry<String, ?> entry : params.entrySet()){
                String key = entry.getKey();
                Object val = entry.getValue();
                if ( val instanceof List){
                    res += generateUrlListParamForKey(key, (List) val);
                }
                else{
                    res += key + "=" + String.valueOf(val) + "&";
                }
            }
        }
        return res;
    }

    protected String generateUrlListParamForKey(String key, List list) {
        String res = "";
        int listIndex = 0;
        for( Object elem : list ){
            String indexedKey = key + "[" + listIndex++ + "]";
            if ( elem instanceof Map){
                for( Map.Entry entry : ((Map<Object,Object>) elem).entrySet()){
                    res += indexedKey + "[" + String.valueOf(entry.getKey()) + "]="
                                      + String.valueOf(entry.getValue()) + "&";
                }
            }
            else{
                res += indexedKey + "=" + String.valueOf(elem) + "&";
            }
        }
        return res;
    }
}
