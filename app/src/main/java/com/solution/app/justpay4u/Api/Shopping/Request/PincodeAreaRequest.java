package com.solution.app.justpay4u.Api.Shopping.Request;

public class PincodeAreaRequest {



    String pincode; String appid; String imei; String regKey; String version; String serialNo; int loginTypeID; String userID;
    String sessionID;String session;

    public PincodeAreaRequest(String pincode, String appid, String imei, String regKey, String version, String serialNo,
                              int loginTypeID, String userID,
                              String sessionID, String session) {
        this.pincode = pincode;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
    }
}
