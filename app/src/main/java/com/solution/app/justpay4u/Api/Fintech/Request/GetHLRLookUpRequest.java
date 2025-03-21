package com.solution.app.justpay4u.Api.Fintech.Request;

/**
 * Created by Vishnu Agarwal on 16,November,2019
 */
public class GetHLRLookUpRequest extends BasicRequest{

    String mobile;

    public GetHLRLookUpRequest(String mobile, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.mobile = mobile;
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
