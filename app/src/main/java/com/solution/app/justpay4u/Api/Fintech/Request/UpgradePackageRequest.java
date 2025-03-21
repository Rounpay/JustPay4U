package com.solution.app.justpay4u.Api.Fintech.Request;

public class UpgradePackageRequest extends BasicRequest {

    public int OutletID;
    public int OpTypeID;
    public int OID;
    String uid;
    int availablePackageId;


    public UpgradePackageRequest(String uid, int availablePackageId, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.uid = uid;
        this.availablePackageId = availablePackageId;
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

    public UpgradePackageRequest(int OpTypeID, int OID, int OutletID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.OutletID = OutletID;
        this.OpTypeID = OpTypeID;
        this.OID = OID;
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
