package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAadhaarOTP {

    @SerializedName("isAppValid")
    @Expose
    private Boolean isAppValid;
    @SerializedName("isVersionValid")
    @Expose
    private Boolean isVersionValid;
    @SerializedName("isPasswordExpired")
    @Expose
    private Boolean isPasswordExpired;
    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("referenceID")
    @Expose
    private Integer referenceID;

    public Boolean getAppValid() {
        return isAppValid;
    }

    public void setAppValid(Boolean appValid) {
        isAppValid = appValid;
    }

    public Boolean getVersionValid() {
        return isVersionValid;
    }

    public void setVersionValid(Boolean versionValid) {
        isVersionValid = versionValid;
    }

    public Boolean getPasswordExpired() {
        return isPasswordExpired;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        isPasswordExpired = passwordExpired;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public Boolean getOTPSent() {
        return isOTPSent;
    }

    public void setOTPSent(Boolean OTPSent) {
        isOTPSent = OTPSent;
    }

    public Boolean getAadharValid() {
        return isAadharValid;
    }

    public void setAadharValid(Boolean aadharValid) {
        isAadharValid = aadharValid;
    }

    public Boolean getNumberLinked() {
        return isNumberLinked;
    }

    public void setNumberLinked(Boolean numberLinked) {
        isNumberLinked = numberLinked;
    }

    public Object getInitiateID() {
        return initiateID;
    }

    public void setInitiateID(Object initiateID) {
        this.initiateID = initiateID;
    }

    public Boolean getCallSDK() {
        return isCallSDK;
    }

    public void setCallSDK(Boolean callSDK) {
        isCallSDK = callSDK;
    }

    @SerializedName("isOTPSent")
    @Expose
    private Boolean isOTPSent;
    @SerializedName("isAadharValid")
    @Expose
    private Boolean isAadharValid;
    @SerializedName("isNumberLinked")
    @Expose
    private Boolean isNumberLinked;
    @SerializedName("initiateID")
    @Expose
    private Object initiateID;
    @SerializedName("isCallSDK")
    @Expose
    private Boolean isCallSDK;

    public Boolean getIsAppValid() {
        return isAppValid;
    }

    public void setIsAppValid(Boolean isAppValid) {
        this.isAppValid = isAppValid;
    }

    public Boolean getIsVersionValid() {
        return isVersionValid;
    }

    public void setIsVersionValid(Boolean isVersionValid) {
        this.isVersionValid = isVersionValid;
    }

    public Boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public void setIsPasswordExpired(Boolean isPasswordExpired) {
        this.isPasswordExpired = isPasswordExpired;
    }
    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
