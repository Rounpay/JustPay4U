package com.solution.app.justpay4u.Api.Fintech.Request;

public class GetDthPackageChannelRequest extends BasicRequest{
    String oid, pid;


    public GetDthPackageChannelRequest(String pid, String oid, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.pid = pid;
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
