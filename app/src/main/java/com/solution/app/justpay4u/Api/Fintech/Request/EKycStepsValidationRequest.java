package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EKycStepsValidationRequest extends BasicRequest{
    @SerializedName("stepID")
    @Expose
    public int stepID;
    @SerializedName("verificationAccount")
    @Expose
    public String verificationAccount;
    @SerializedName("ifsc")
    @Expose
    public String ifsc;
    @SerializedName("bankID")
    @Expose
    public int bankID;
    @SerializedName("otp")
    @Expose
    public String otp;
    @SerializedName("reffID")
    @Expose
    public String reffID;
    @SerializedName("companyTypeID")
    @Expose
    public String companyTypeID;
    @SerializedName("director")
    @Expose
    public String director;
    @SerializedName("isSkip")
    @Expose
    public boolean isSkip;
    @SerializedName("isConcent")
    @Expose
    public boolean isConcent;



    public EKycStepsValidationRequest(String verificationAccount, String companyTypeID, boolean isSkip, boolean isConcent, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.verificationAccount = verificationAccount;
        this.companyTypeID = companyTypeID;
        this.isSkip = isSkip;
        this.isConcent = isConcent;
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

    public EKycStepsValidationRequest(String verificationAccount, String ifsc, int bankID, boolean isSkip, boolean isConcent, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.verificationAccount = verificationAccount;
        this.ifsc = ifsc;
        this.bankID = bankID;
        this.isSkip = isSkip;
        this.isConcent = isConcent;
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

    public EKycStepsValidationRequest(boolean isSkip, String verificationAccount, String director, boolean isConcent, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.verificationAccount = verificationAccount;
        this.director = director;
        this.isSkip = isSkip;
        this.isConcent = isConcent;
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

    public EKycStepsValidationRequest(String otp, String reffID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.otp = otp;
        this.reffID = reffID;
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

    public EKycStepsValidationRequest(int stepID, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.stepID = stepID;
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
