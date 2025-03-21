package com.solution.app.justpay4u.Api.Fintech.Request;

public class DTHChannelPlanInfoRequest extends BasicRequest{
    String OID, PackageID;

    public DTHChannelPlanInfoRequest(String PackageID, String OID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.PackageID = PackageID;
        this.OID = OID;
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
