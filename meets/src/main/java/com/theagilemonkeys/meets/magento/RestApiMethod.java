package com.theagilemonkeys.meets.magento;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.theagilemonkeys.meets.ApiMethod;
import com.theagilemonkeys.meets.utils.MeetsSerializable;

import java.net.HttpURLConnection;
import java.net.URL;

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
        URL url = new URL(generateUrl());
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

        // Set headers
        urlConn.setRequestProperty("Accept", "application/json");
        urlConn.setRequestProperty("Content-Type", "application/json");

        String basicAuthName = getBasicAuthName();
        String basicAuthPass = getBasicAuthPass();
        if (basicAuthName != null && basicAuthPass != null) {
            byte[] token = (basicAuthName + ":" + basicAuthPass).getBytes();
            urlConn.setRequestProperty("Authorization", "Basic " + new String(Base64.encodeBase64(token)));
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixInAnnotations(MeetsSerializable.class, MagentoDeserializerOptions.class);

        return (RESULT) mapper.readValue(urlConn.getInputStream(), responseClass);
    }
}

@JsonTypeInfo(use= JsonTypeInfo.Id.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
interface MagentoDeserializerOptions {

}

