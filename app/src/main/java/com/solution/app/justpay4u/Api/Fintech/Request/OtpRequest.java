package com.solution.app.justpay4u.Api.Fintech.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpRequest {
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("oTPSession")
    @Expose
    private String oTPSession;
    @SerializedName("oTPType")
    @Expose
    private int oTPType;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("regKey")
    @Expose
    private String regKey;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("serialNo")
    @Expose
    private String serialNo;

    public OtpRequest(String otp, String oTPSession, int oTPType, String domain, String appid, String imei, String regKey, String version, String serialNo) {
        this.otp = otp;
        this.oTPSession = oTPSession;
        this.oTPType = oTPType;
        this.domain = domain;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
    }


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOTPSession() {
        return oTPSession;
    }

    public void setOTPSession(String oTPSession) {
        this.oTPSession = oTPSession;
    }

    public int getOTPType() {
        return oTPType;
    }

    public void setOTPType(int oTPType) {
        this.oTPType = oTPType;
    }

    public Object getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Object getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Object getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Object getRegKey() {
        return regKey;
    }

    public void setRegKey(String regKey) {
        this.regKey = regKey;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

}
