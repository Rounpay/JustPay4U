package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.ChildRoles;
import com.solution.app.justpay4u.Api.Fintech.Object.LoginData;

import java.util.ArrayList;

/**
 * Created by Vishnu on 08-03-2017.
 */

public class LoginResponse {

    /*
        @SerializedName("data")
        @Expose
        public BalanceData balanceData;
    */
    @SerializedName(value = "isAccountStatement", alternate = "IsAccountStatement")
    @Expose
    public boolean isAccountStatement;
    @SerializedName(value = "isAreaMaster", alternate = "IsAreaMaster")
    @Expose
    public boolean isAreaMaster;
    @SerializedName("childRoles")
    @Expose
    public ArrayList<ChildRoles> childRoles;
    @SerializedName("checkID")
    @Expose
    public int checkID;
    @SerializedName("isBinaryon")
    @Expose
    public int isBinaryon;
    @SerializedName(value = "dashPreferenceApp", alternate = "dashPreference")
    @Expose
    public int dashPreference;    //MLM = 1; Fintech = 2; Shopping = 3;
    @SerializedName("isReferralEditable")
    @Expose
    public boolean isReferralEditable;
    @SerializedName("isMLM")
    @Expose
    public boolean isMLM;
    @SerializedName("isECommerceAllowed")
    @Expose
    public boolean isECommerceAllowed;
    @SerializedName("isFintechServiceOn")
    @Expose
    public boolean isFintechServiceOn;
    @SerializedName("isPasswordExpired")
    @Expose
    public boolean isPasswordExpired;
    @SerializedName("isPlanServiceUpdated")
    @Expose
    public boolean isPlanServiceUpdated;
    @SerializedName(value = "isDenominationIncentive", alternate = "IsDenominationIncentive")
    @Expose
    public boolean isDenominationIncentive;
    @SerializedName("data")
    @Expose
    private LoginData data;
    @SerializedName(value = "leg", alternate = "legs")
    @Expose
    private String leg;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("otpSession")
    @Expose
    private String otpSession;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("loginUrl")
    @Expose
    private String loginUrl;

    @SerializedName("signupRefferalId")
    @Expose
    private int signupRefferalId;
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
    @SerializedName("isDTHInfo")
    @Expose
    private boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private boolean isRoffer;
    @SerializedName("isReferral")
    @Expose
    private boolean isReferral;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;
    @SerializedName("isCountryCodeRequired")
    @Expose
    private boolean isCountryCodeRequired;
    @SerializedName(value = "IsDTHInfoCall", alternate = "isDTHInfoCall")
    @Expose
    private boolean isDTHInfoCall;
    @SerializedName(value = "IsHeavyRefresh", alternate = "isHeavyRefresh")
    @Expose
    private boolean isHeavyRefresh;
    @SerializedName(value = "IsRealAPIPerTransaction", alternate = "isRealAPIPerTransaction")
    @Expose
    private boolean isRealAPIPerTransaction;
    @SerializedName(value = "IsTargetShow", alternate = "isTargetShow")
    @Expose
    private boolean isTargetShow;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public boolean isTargetShow() {
        return isTargetShow;
    }

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public int getIsBinaryon() {
        return isBinaryon;
    }

    public boolean isReferralEditable() {
        return isReferralEditable;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public int getSignupRefferalId() {
        return signupRefferalId;
    }

    public String getLeg() {
        return leg != null && !leg.isEmpty() ? leg : "L";
    }

    public String getOtpSession() {
        return otpSession;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public boolean isCountryCodeRequired() {
        return isCountryCodeRequired;
    }

    public String getMobileNo() {
        return mobileNo;
    }
/* public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(int loginTypeID) {
        this.loginTypeID = loginTypeID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegKey() {
        return regKey;
    }

    public void setRegKey(String regKey) {
        this.regKey = regKey;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }*/

    /*public BalanceData getBalanceData() {
        return balanceData;
    }

    public void setBalanceData(BalanceData balanceData) {
        this.balanceData = balanceData;
    }*/

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public boolean isAreaMaster() {
        return isAreaMaster;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public void setDTHInfo(boolean DTHInfo) {
        isDTHInfo = DTHInfo;
    }

    public ArrayList<ChildRoles> getChildRoles() {
        return childRoles;
    }

    public boolean isRoffer() {
        return isRoffer;
    }

    public void setRoffer(boolean roffer) {
        isRoffer = roffer;
    }

    public boolean isHeavyRefresh() {
        return isHeavyRefresh;
    }

    public void setHeavyRefresh(boolean heavyRefresh) {
        isHeavyRefresh = heavyRefresh;
    }

    public boolean isRealAPIPerTransaction() {
        return isRealAPIPerTransaction;
    }

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

    public boolean isPlanServiceUpdated() {
        return isPlanServiceUpdated;
    }

    public boolean isAccountStatement() {
        return isAccountStatement;
    }

    public boolean isReferral() {
        return isReferral;
    }

    public int getDashPreference() {
        //MLM = 1; Fintech = 2; Shopping = 3;
        return dashPreference;
    }

    public boolean isMLM() {
        return isMLM;
    }

    public boolean isECommerceAllowed() {
        return isECommerceAllowed;
    }

    public boolean isFintechServiceOn() {
        return isFintechServiceOn;
    }

    public boolean isDenominationIncentive() {
        return isDenominationIncentive;
    }
}
