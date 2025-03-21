package com.solution.app.justpay4u.Api.Fintech.Object;

public class GetDthPackageRequest {
    String oid;
    String userID;
    String sessionID;
    String session;
    String appid;
    String imei;
    String regKey;
    String version;
    String serialNo;
    int loginTypeID;

    public GetDthPackageRequest(String oid, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
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
