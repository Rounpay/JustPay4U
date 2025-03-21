package com.solution.app.justpay4u.Api.Fintech.Request;

public class CallBackRequest extends BasicRequest{

    String mobileNo;


    public CallBackRequest(String mobileNo, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.mobileNo = mobileNo;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
    }
}
