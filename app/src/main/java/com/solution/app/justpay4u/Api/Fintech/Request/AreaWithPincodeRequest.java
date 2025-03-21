package com.solution.app.justpay4u.Api.Fintech.Request;

public class AreaWithPincodeRequest extends BasicRequest{
    private String pincode;


    public AreaWithPincodeRequest(String pincode, String appid, String imei, String regKey, String version, String serialNo, int loginTypeID, String userID,
                                  String sessionID, String session) {
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.pincode = pincode;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;

    }
}
