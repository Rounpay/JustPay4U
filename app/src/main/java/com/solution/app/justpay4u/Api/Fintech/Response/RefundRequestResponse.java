package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefundRequestResponse {

    @SerializedName("refID")
    @Expose
    private String refID;
    @SerializedName("isOTPRequired")
    @Expose
    private boolean isOTPRequired;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    private int checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;

    public String getRefID() {
        return refID;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }

    public boolean getOTPRequired() {
        return isOTPRequired;
    }

    public void setOTPRequired(boolean OTPRequired) {
        isOTPRequired = OTPRequired;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public void setVersionValid(boolean versionValid) {
        isVersionValid = versionValid;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public void setAppValid(boolean appValid) {
        isAppValid = appValid;
    }

    public int getCheckID() {
        return checkID;
    }

    public void setCheckID(int checkID) {
        this.checkID = checkID;
    }

    public boolean getPasswordExpired() {
        return isPasswordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        isPasswordExpired = passwordExpired;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public boolean getLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public void setLookUpFromAPI(boolean lookUpFromAPI) {
        isLookUpFromAPI = lookUpFromAPI;
    }
}
