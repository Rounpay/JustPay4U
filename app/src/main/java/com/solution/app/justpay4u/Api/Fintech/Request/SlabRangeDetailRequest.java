package com.solution.app.justpay4u.Api.Fintech.Request;

public class SlabRangeDetailRequest  extends BasicRequest{

    int oid;


    public SlabRangeDetailRequest(int oid, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
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
