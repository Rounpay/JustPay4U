package com.solution.app.justpay4u.Fintech.Employee.Api.Request;

public class MapPointRequest {

    public String userID;

    public String sessionID;

    public String session;

    public String appid;

    public String imei;

    public String regKey;

    public String version;

    public String serialNo;

    public int loginTypeID;
    public int searchId;



    public MapPointRequest(int searchId, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.searchId = searchId;
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
