package com.theagilemonkeys.meets.magento;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.theagilemonkeys.meets.ApiMethod;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class RestApiMethod<RESULT> extends ApiMethod<RESULT> {

    //These properties needs to be configured on app init
    public static String baseUrl;


    public RestApiMethod(Class magentoModelClass) {
        super(magentoModelClass);
    }

    @Override
    protected String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public RESULT loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest( new GenericUrl(generateUrl()) );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept("application/json")
                   .setContentType("application/json");

        String basicAuthName = getBasicAuthName();
        String basicAuthPass = getBasicAuthPass();
        if (basicAuthName != null && basicAuthPass != null){
            httpHeaders.setBasicAuthentication(basicAuthName, basicAuthPass);
        }

        HttpResponse response = request.setHeaders(httpHeaders)
                                       .setParser(new JacksonFactory().createJsonObjectParser())
                                       .execute();

        Object res = response.parseAs(responseClass);

        return (RESULT) res;
    }
}

