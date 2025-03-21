package com.solution.app.justpay4u.ApiHits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;


public class UplinePoolCountResponse {

    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    public int checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    public boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("isLookUpFromAPI")
    @Expose
    public boolean isLookUpFromAPI;
    @SerializedName("isDTHInfoCall")
    @Expose
    public boolean isDTHInfoCall;
    @SerializedName("isShowPDFPlan")
    @Expose
    public boolean isShowPDFPlan;
    @SerializedName("sid")
    @Expose
    public String sid;
    @SerializedName("pCode")
    @Expose
    public String pCode;
    @SerializedName("isOTPRequired")
    @Expose
    public boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    public boolean isResendAvailable;
    @SerializedName("isDTHInfo")
    @Expose
    public boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    public boolean isRoffer;
    @SerializedName("isGreen")
    @Expose
    public boolean isGreen;
    @SerializedName("isBulkQRGeneration")
    @Expose
    public boolean isBulkQRGeneration;
    @SerializedName("isCoin")
    @Expose
    public boolean isCoin;
    @SerializedName("legs")
    @Expose
    public String legs;
    @SerializedName("userId")
    @Expose
    public String userId;
    @SerializedName("loginUrl")
    @Expose

    public String loginUrl;
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("isSattlemntAccountVerify")
    @Expose
    public boolean isSattlemntAccountVerify;
    @SerializedName("fiatCurrID")
    @Expose
    public int fiatCurrID;
    @SerializedName("fiatCurrSymbol")
    @Expose

    public String fiatCurrSymbol;
    @SerializedName("fiatCurrName")
    @Expose
    public String fiatCurrName;
    @SerializedName("fiatTechnologyId")
    @Expose

    public int fiatTechnologyId;
    @SerializedName("fiatImageUrl")
    @Expose
    public String fiatImageUrl;

    @SerializedName("data")
    @Expose
    public ArrayList<DataPoolCount> data;

    public ArrayList<DataPoolCount> getData() {
        return data;
    }

    public void setData(ArrayList<DataPoolCount> data) {
        this.data = data;
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

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public void setVersionValid(boolean versionValid) {
        isVersionValid = versionValid;
    }

    public boolean isAppValid() {
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

    public boolean isPasswordExpired() {
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

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public void setLookUpFromAPI(boolean lookUpFromAPI) {
        isLookUpFromAPI = lookUpFromAPI;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

    public void setDTHInfoCall(boolean DTHInfoCall) {
        isDTHInfoCall = DTHInfoCall;
    }

    public boolean isShowPDFPlan() {
        return isShowPDFPlan;
    }

    public void setShowPDFPlan(boolean showPDFPlan) {
        isShowPDFPlan = showPDFPlan;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public void setOTPRequired(boolean OTPRequired) {
        isOTPRequired = OTPRequired;
    }

    public boolean isResendAvailable() {
        return isResendAvailable;
    }

    public void setResendAvailable(boolean resendAvailable) {
        isResendAvailable = resendAvailable;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public void setDTHInfo(boolean DTHInfo) {
        isDTHInfo = DTHInfo;
    }

    public boolean isRoffer() {
        return isRoffer;
    }

    public void setRoffer(boolean roffer) {
        isRoffer = roffer;
    }

    public boolean isGreen() {
        return isGreen;
    }

    public void setGreen(boolean green) {
        isGreen = green;
    }

    public boolean isBulkQRGeneration() {
        return isBulkQRGeneration;
    }

    public void setBulkQRGeneration(boolean bulkQRGeneration) {
        isBulkQRGeneration = bulkQRGeneration;
    }

    public boolean isCoin() {
        return isCoin;
    }

    public void setCoin(boolean coin) {
        isCoin = coin;
    }

    public String getLegs() {
        return legs;
    }

    public void setLegs(String legs) {
        this.legs = legs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSattlemntAccountVerify() {
        return isSattlemntAccountVerify;
    }

    public void setSattlemntAccountVerify(boolean sattlemntAccountVerify) {
        isSattlemntAccountVerify = sattlemntAccountVerify;
    }

    public int getFiatCurrID() {
        return fiatCurrID;
    }

    public void setFiatCurrID(int fiatCurrID) {
        this.fiatCurrID = fiatCurrID;
    }

    public String getFiatCurrSymbol() {
        return fiatCurrSymbol;
    }

    public void setFiatCurrSymbol(String fiatCurrSymbol) {
        this.fiatCurrSymbol = fiatCurrSymbol;
    }

    public String getFiatCurrName() {
        return fiatCurrName;
    }

    public void setFiatCurrName(String fiatCurrName) {
        this.fiatCurrName = fiatCurrName;
    }

    public int getFiatTechnologyId() {
        return fiatTechnologyId;
    }

    public void setFiatTechnologyId(int fiatTechnologyId) {
        this.fiatTechnologyId = fiatTechnologyId;
    }

    public String getFiatImageUrl() {
        return fiatImageUrl;
    }

    public void setFiatImageUrl(String fiatImageUrl) {
        this.fiatImageUrl = fiatImageUrl;
    }
}
