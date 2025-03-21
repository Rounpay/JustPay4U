package com.solution.app.justpay4u.Api.Fintech.Request;

public class InitiateMiniBankRequest extends BasicRequest{

    String sDKType, oid, amount;

    private String Longitude;
    private String Lattitude;

    public InitiateMiniBankRequest(String Lattitude, String Longitude, String sDKType, String oid, String amount, String userID,
                                   int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.sDKType = sDKType;
        this.oid = oid;
        this.amount = amount;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
    }
}
