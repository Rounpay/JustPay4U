package com.solution.app.justpay4u.Api.Fintech.Request;

public class MapQRToUserRequest  extends BasicRequest{

    String QRIntent;



    public MapQRToUserRequest(String QRIntent, String userID, int loginTypeID, String appid, String imei,
                              String regKey, String version, String serialNo, String sessionID, String session) {
        this.QRIntent = QRIntent;
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
