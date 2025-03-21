package com.solution.app.justpay4u.Api.Fintech.Request;

public class GetAdditionalServiceRequest extends BasicRequest{

    public int OutletID, OpTypeID;


    public GetAdditionalServiceRequest(int OutletID, int OpTypeID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.OutletID = OutletID;
        this.OpTypeID = OpTypeID;
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
