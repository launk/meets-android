package com.theagilemonkeys.meets.cloud;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theagilemonkeys.meets.ApiMethod;
import com.theagilemonkeys.meets.utils.MeetsSerializable;
import org.apache.commons.codec.binary.Base64;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Cloud Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class RestApiMethod<RESULT> extends ApiMethod<RESULT> {

    public RestApiMethod(Class magentoModelClass) {
        super(magentoModelClass);
    }

    @Override
    protected String getBaseUrl() {
        return config.getRestBaseUrl();
    }

    @Override
    public RESULT loadDataFromNetwork() throws Exception {
        URL url = new URL(generateUrl());
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

        // Set headers
        urlConn.setRequestProperty("Accept", "application/json");
        urlConn.setRequestProperty("Content-Type", "application/json");

        String basicAuthName = config.getBasicAuthName();
        String basicAuthPass = config.getBasicAuthPass();
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

