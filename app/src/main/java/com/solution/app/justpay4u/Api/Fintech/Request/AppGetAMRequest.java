package com.solution.app.justpay4u.Api.Fintech.Request;

public class AppGetAMRequest extends BasicRequest{

    private String walletTypeID;
    private String parentid;


    public AppGetAMRequest(String appid, String imei, String regKey, String serialNo, String session, String sessionID, String version, int loginTypeID, String userID) {
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.version = version;
        this.loginTypeID = loginTypeID;
        this.userID = userID;
    }
}
