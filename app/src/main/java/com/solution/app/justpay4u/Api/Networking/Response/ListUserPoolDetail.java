package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListUserPoolDetail {
    @SerializedName("userID")
    @Expose
    private int userID;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("signupDate")
    @Expose
    private String signupDate;
    @SerializedName("lastRechargeDate")
    @Expose
    private String lastRechargeDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalCount")
    @Expose
    private int totalCount;
    @SerializedName("deactiveCount")
    @Expose
    private int deactiveCount;
    @SerializedName("activeCount")
    @Expose
    private int activeCount;
    @SerializedName("levelNo")
    @Expose
    private String levelNo;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    public String getLastRechargeDate() {
        return lastRechargeDate;
    }

    public void setLastRechargeDate(String lastRechargeDate) {
        this.lastRechargeDate = lastRechargeDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getDeactiveCount() {
        return deactiveCount;
    }

    public void setDeactiveCount(int deactiveCount) {
        this.deactiveCount = deactiveCount;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

}
