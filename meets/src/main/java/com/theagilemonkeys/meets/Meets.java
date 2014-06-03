package com.theagilemonkeys.meets;

import android.content.Context;

import com.octo.android.robospice.SpiceManager;
import com.theagilemonkeys.meets.magento.RestApiMethod;
import com.theagilemonkeys.meets.magento.SoapApiMethod;
import com.theagilemonkeys.meets.models.base.MeetsFactory;
import com.theagilemonkeys.meets.models.base.MeetsListener;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */
public class Meets {
//    private static final String restBaseUrl = "/api/rest/";
//    private static final String soapBaseUrl = "/api/v2_soap/";
//    private static final String soapWsdl = "/api/v2_soap/?wsdl";
//    public static int storeId;
//    public static int websiteId;
//
//    // Package private spiceManager.
//    static SpiceManager spiceManager = new SpiceManager(MeetsSpiceService.class);
    private static MeetsDefaultConfig config;

    public static void init(MeetsDefaultConfig config) {
        Meets.config = config;
    }

    public static MeetsDefaultConfig getConfig(){
        return config;
    }

    /**
     * Sets a global listener that it's always called after any specific listener.
     * @param listener A instance of MeetsListener. Be careful if you pass a non static inner class instance here,
     *                 because it keeps a reference to parent class. If it is an Activity, there will be a potential
     *                 memory leak (This doesn't happen when parent class is the Application)
     */
    public static void setGlobalListener(MeetsListener listener) {
        ApiMethodModelHelper.globalListener = listener;
    }

//
//    /**
//     * Initializer method for Meets. You will usually call it in your {@code App.onCreate} method.
//     * @param applicationContext The application context
//     * @param factory The MeetsFactory implementation instance that will be used.
//     *                You can use {@link com.theagilemonkeys.meets.magento.models.base.MageMeetsFactory MageMeetsFactory}.
//     * @param hostUrl The url of your e-commerce host. For example: {@code "http://www.example.com" }
//     * @param soapApiUser The SOAP API user of your host
//     * @param soapApiPass The SOAP API password of your host
//     */
//    public static void init(Context applicationContext, MeetsFactory factory, String hostUrl, String soapApiUser, String soapApiPass) {
//        spiceManager.start(applicationContext);
//        MeetsFactory.setInstance(factory);
//        SoapApiMethod.soapApiUser = soapApiUser;
//        SoapApiMethod.soapApiPass = soapApiPass;
//
//        setRestHostUrl(hostUrl);
//        setSoapHostUrl(hostUrl);
//    }
//
//    public static void init(Context applicationContext, MeetsFactory factory, String hostUrl, String soapApiUser, String soapApiPassword,
//                            int storeId, int websiteId) {
//        init(applicationContext, factory, hostUrl, soapApiUser, soapApiPassword);
//        Meets.storeId = storeId;
//        Meets.websiteId = websiteId;
//        // In Magento Soap API, "store" param has different names among functions
//        ApiMethod.fixedParams.put("store", storeId);
//        ApiMethod.fixedParams.put("storeId", storeId);
//        ApiMethod.fixedParams.put("storeView", storeId);
//    }
//
//    public static void init(Context applicationContext, MeetsFactory factory, String hostUrl, String soapApiUser, String soapApiPass,
//                            int storeId, int websiteId, String basicAuthUser, String basicAuthPassword) {
//        init(applicationContext, factory, hostUrl, soapApiUser, soapApiPass, storeId, websiteId);
//        RestApiMethod.setBasicAuth(basicAuthUser, basicAuthPassword);
//        SoapApiMethod.setBasicAuth(basicAuthUser, basicAuthPassword);
//    }
//
//    public static void init(Context applicationContext, MeetsFactory factory, String restHostUrl, String soapHostUrl, String soapApiUser, String soapApiPass,
//                            int storeId, int websiteId, String basicAuthUser, String basicAuthPassword) {
//        init(applicationContext, factory, restHostUrl, soapApiUser, soapApiPass, storeId, websiteId, basicAuthUser, basicAuthPassword);
//        setSoapHostUrl(soapHostUrl);
//    }
//
//    /**
//     * Allow you to set a different url for rest calls (i.e. a proxy)
//     * @param restHostUrl The rest host url
//     */
//    public static void setRestHostUrl(String restHostUrl) {
//        RestApiMethod.baseUrl = restHostUrl.replaceAll("/$", "") + restBaseUrl;
//    }
//
//    /**
//     * Allow you to set a different url for soap calls (i.e. a proxy)
//     * @param soapHostUrl The soap host url
//     */
//    public static void setSoapHostUrl(String soapHostUrl) {
//        SoapApiMethod.baseUrl = soapHostUrl.replaceAll("/$", "") + soapBaseUrl;
//        SoapApiMethod.soapNamespace = soapHostUrl.replaceAll("/$", "") + soapWsdl;
//    }
//
//    /**
//     * Returns the underlying SpiceManager used in all requests.
//     * @return SpiceManager
//     */
//    public static SpiceManager getSpiceManager() {
//        return spiceManager;
//    }
}