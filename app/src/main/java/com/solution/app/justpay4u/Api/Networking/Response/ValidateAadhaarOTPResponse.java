package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateAadhaarOTPResponse {
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
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("referenceID")
    @Expose
    private Integer referenceID;
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
    public String getFullName() {
        return fullName;
    }
    public String getAddress() {
        return address;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public Boolean getIsOTPSent() {
        return isOTPSent;
    }

    public void setIsOTPSent(Boolean isOTPSent) {
        this.isOTPSent = isOTPSent;
    }

    public Boolean getIsAadharValid() {
        return isAadharValid;
    }

    public void setIsAadharValid(Boolean isAadharValid) {
        this.isAadharValid = isAadharValid;
    }

    public Boolean getIsNumberLinked() {
        return isNumberLinked;
    }

    public void setIsNumberLinked(Boolean isNumberLinked) {
        this.isNumberLinked = isNumberLinked;
    }

    public Object getInitiateID() {
        return initiateID;
    }

    public void setInitiateID(Object initiateID) {
        this.initiateID = initiateID;
    }

    public Boolean getIsCallSDK() {
        return isCallSDK;
    }

    public void setIsCallSDK(Boolean isCallSDK) {
        this.isCallSDK = isCallSDK;
    }
}
