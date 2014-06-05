package com.theagilemonkeys.meets.request;

import org.jdeferred.Deferred;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public interface RequestWrapper<RESULT>  {

    void makeRequest(Deferred deferred);

    void makeRequest(Deferred deferred, String cacheKey);
}
