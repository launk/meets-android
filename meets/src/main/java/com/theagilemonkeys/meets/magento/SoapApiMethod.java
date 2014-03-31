package com.theagilemonkeys.meets.magento;

import android.util.Log;

import com.theagilemonkeys.meets.ApiMethod;
import com.theagilemonkeys.meets.utils.soap.SoapParser;

import org.jdeferred.Deferred;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class SoapApiMethod<RESULT> extends ApiMethod<RESULT> {
    private static final String TAG = "----> SoapApiMethod";
    private static String apiSessionId;
    private static final Object lock = new Object();

    public static final String API_SESSION_EXPIRED_FAULTCODE = "5";

    //These properties need to be configured on app init
    public static String baseUrl;
    public static String soapApiUser;
    public static String soapApiPass;
    public static String soapNamespace;

    public static int timeout = 1 * 60 * 1000; // 1 minute timeout

    public SoapApiMethod(Class magentoModelClass) {
        super(magentoModelClass);
    }
    @Override
    public Deferred run(Map<String, Object> params, List<String> urlExtraSegments) {
        return super.run(params, urlExtraSegments);
    }

    @Override
    protected String getBaseUrl() {
        return baseUrl;
    }

    protected void parseResponse(Object response, RESULT model) throws Exception {
        SoapParser.parse((SoapObject) response, model);
    }

    @Override
    public RESULT loadDataFromNetwork() throws Exception {
//            Log.d(TAG, "Entra hilo " + Thread.currentThread().getId() + ". Lock id: " + lock);
            ensureApiLogin();

            Object res = send(getMethodName(), params);

            if (SoapParser.isPrimitiveOrInmutable(responseClass)) {
//                Log.d(TAG, "Sale hilo " + Thread.currentThread().getId() + ". Lock id: " + lock);
                return (RESULT) res;
            } else {
                RESULT model = (RESULT) responseClass.newInstance();
                parseResponse(res, model);
//                Log.d(TAG, "Sale hilo " + Thread.currentThread().getId() + ". Lock id: " + lock);
                return model;
            }
    }

    private Object send(String method, Map<String, Object> params) throws IOException, XmlPullParserException {
        HttpTransportSE httpTransport = new HttpTransportSE(baseUrl,timeout);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = false;
        soapEnvelope.xsd = SoapSerializationEnvelope.XSD;
        soapEnvelope.enc = SoapSerializationEnvelope.ENC;

        SoapObject request = new SoapObject(soapNamespace, method);

        if (params == null)
            params = new HashMap<String, Object>();

        if (apiSessionIsValid())
            params.put("sessionId", getApiSessionId());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            if (val instanceof KvmSerializable) {
                request.addProperty(buildPropertyInfo(key, (KvmSerializable) val));
            } else {
                request.addProperty(key, val);
            }
        }

        soapEnvelope.setOutputSoapObject(request);

        List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
        String basicAuthName = getBasicAuthName();
        String basicAuthPass = getBasicAuthPass();
        if (basicAuthName != null && basicAuthPass != null) {
            byte[] token = (basicAuthName + ":" + basicAuthPass).getBytes();
            headerList.add(new HeaderProperty("Authorization", "Basic " + org.kobjects.base64.Base64.encode(token)));
        }

        try {
            synchronized (lock) {
                httpTransport.call("", soapEnvelope, headerList);
            }
            return soapEnvelope.getResponse();
        } catch (SoapFault e) {
            if (API_SESSION_EXPIRED_FAULTCODE.equals(e.faultcode)) {
                // Session expired. Set apiSessionId to null and let it reconnect on retry
                setApiSessionId(null);
                Log.d(getClass().getSimpleName(), "Soap api session expired. Trying to reconnect");
            }
            throw e;
        }
    }

    private Object send(String method, String... params) throws IOException, XmlPullParserException {
        Map<String, Object> finalParams = new HashMap<String, Object>();

        if ( params != null )
            for(int i = 0; i < params.length; i += 2)
                finalParams.put(params[i], params[i+1]);

        return send(method, finalParams);
    }

    private PropertyInfo buildPropertyInfo(String name, KvmSerializable val) {
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName(name);
        propertyInfo.setValue(val);
        propertyInfo.setType(val.getClass());
        return propertyInfo;
    }

    private void ensureApiLogin() throws IOException, XmlPullParserException {
        synchronized (lock) {
            if ( getApiSessionId() == null )
                setApiSessionId((String) send("login", "username", soapApiUser, "apiKey", soapApiPass));
        }

    }

    private static void setApiSessionId(String id) {
        synchronized (lock) {
            apiSessionId = id;
        }
    }

    private static String getApiSessionId() {
        synchronized (lock) {
            return apiSessionId;
        }
    }


    private static boolean apiSessionIsValid(){
        synchronized (lock) {
            return apiSessionId != null;
        }
    }
}