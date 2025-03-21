package com.solution.app.justpay4u.Api.Fintech.Request;

/**
 * Created by Vishnu Agarwal on 20,January,2020
 */
public class ResendOtpRequest {
    String oTPSession;
    String domain;
    String appid;
    String imei;
    String regKey;
    String version;
    String serialNo;

    public ResendOtpRequest(String oTPSession, String domain, String appid, String imei, String regKey, String version, String serialNo) {
        this.oTPSession = oTPSession;
        this.domain = domain;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
    }
}
