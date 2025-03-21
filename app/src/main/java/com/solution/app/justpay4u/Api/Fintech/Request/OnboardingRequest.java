package com.solution.app.justpay4u.Api.Fintech.Request;

public class OnboardingRequest extends BasicRequest {

    String oid;
    String outletID;
    int bioAuthType;
    String otp, OTPRefID, pidData;
    boolean isBio;

    public OnboardingRequest(String otp, String oid, String outletID, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, int loginTypeID) {
        this.otp = otp;
        this.oid = oid;
        this.outletID = outletID;
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

    public OnboardingRequest(int bioAuthType, boolean isBio, String otp, String OTPRefID, String pidData, String oid, String outletID, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, int loginTypeID) {
        this.bioAuthType = bioAuthType;
        this.isBio = isBio;
        this.otp = otp;
        this.OTPRefID = OTPRefID;
        this.pidData = pidData;
        this.oid = oid;
        this.outletID = outletID;
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
