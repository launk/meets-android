package com.theagilemonkeys.meets;

import com.theagilemonkeys.meets.models.base.MeetsFactory;

/**
 * Android Meets SDK
 * Original work Copyright (c) 2014 [TheAgileMonkeys]
 *
 * @author Álvaro López Espinosa
 */

@SuppressWarnings("unchecked")
public class MeetsDefaultConfig<CONFIG extends MeetsDefaultConfig> {

    public static final String restPath = "/api/rest/";
    public static final String soapPath = "/api/v2_soap/";
    public static final String soapWsdlPath = "/api/v2_soap/?wsdl";

    private MeetsFactory factory;
    private String restBaseUrl;
    private String soapBaseUrl;
    private String soapWsdlUrl;
    private String soapApiUser;
    private String soapApiPass;
    
    private int websiteId;
    private int storeId;

    private String basicAuthName;
    private String basicAuthPass;

    public MeetsDefaultConfig(MeetsFactory factory, String hostUrl) {
        setFactory(factory);
        hostUrl = hostUrl.replaceAll("/$", "");
        setRestBaseUrl(hostUrl + restPath);
        setSoapBaseUrl(hostUrl + soapPath);
        setSoapWsdlUrl(hostUrl + soapWsdlPath);
    }

    // Getters

    public MeetsFactory getFactory() {
        return factory;
    }

    public String getRestBaseUrl() {
        return restBaseUrl;
    }

    public String getSoapBaseUrl() {
        return soapBaseUrl;
    }

    public String getSoapWsdlUrl() {
        return soapWsdlUrl;
    }

    public String getSoapApiUser() {
        return soapApiUser;
    }

    public String getSoapApiPass() {
        return soapApiPass;
    }

    public int getWebsiteId() {
        return websiteId;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getBasicAuthName() {
        return basicAuthName;
    }

    public String getBasicAuthPass() {
        return basicAuthPass;
    }
    // Setters

    public CONFIG setFactory(MeetsFactory factory) {
        this.factory = factory;
        return (CONFIG) this;
    }

    public CONFIG setRestBaseUrl(String restBaseUrl) {
        this.restBaseUrl = restBaseUrl;
        return (CONFIG) this;
    }

    public CONFIG setSoapBaseUrl(String soapBaseUrl) {
        this.soapBaseUrl = soapBaseUrl;
        return (CONFIG) this;
    }

    public CONFIG setSoapWsdlUrl(String soapWsdlUrl) {
        this.soapWsdlUrl = soapWsdlUrl;
        return (CONFIG) this;
    }

    public CONFIG setSoapApiUser(String soapApiUser) {
        this.soapApiUser = soapApiUser;
        return (CONFIG) this;
    }

    public CONFIG setSoapApiPass(String soapApiPass) {
        this.soapApiPass = soapApiPass;
        return (CONFIG) this;
    }

    public CONFIG setWebsiteId(int websiteId) {
        this.websiteId = websiteId;
        return (CONFIG) this;
    }

    public CONFIG setStoreId(int storeId) {
        this.storeId = storeId;
        return (CONFIG) this;
    }

    public CONFIG setBasicAuth(String name, String pass){
        basicAuthName = name;
        basicAuthPass = pass;
        return (CONFIG) this;
    }
}
