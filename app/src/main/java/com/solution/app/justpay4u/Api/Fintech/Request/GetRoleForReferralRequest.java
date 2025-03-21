package com.solution.app.justpay4u.Api.Fintech.Request;

public class GetRoleForReferralRequest {

    String domain;
    String appid;
    String imei;
    String regKey;
    String version;
    String serialNo;
    String referralID;

    public GetRoleForReferralRequest(String referralID, String domain, String appid, String imei, String regKey, String version, String serialNo) {
        this.referralID = referralID;
        this.domain = domain;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;

    }
}
