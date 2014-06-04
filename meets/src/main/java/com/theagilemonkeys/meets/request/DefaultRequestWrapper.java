package com.theagilemonkeys.meets.request;

import com.theagilemonkeys.meets.ApiMethod;
import org.jdeferred.Deferred;

/**
 * Created by kloster on 4/06/14.
 */
public class DefaultRequestWrapper<RESULT> implements RequestWrapper<RESULT> {
    private ApiMethod<RESULT> method;

    public DefaultRequestWrapper(ApiMethod<RESULT> method) {
        this.method = method;
    }

    @Override
    public void makeRequest(Deferred deferred) {
        makeRequest(deferred, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void makeRequest(Deferred deferred, String cacheKey){
        try {
            RESULT res = method.loadDataFromNetwork();
            deferred.resolve(res);
        }
        catch (Exception e) {
            deferred.reject(e);
            throw new RuntimeException(e);
        }
    }
}
