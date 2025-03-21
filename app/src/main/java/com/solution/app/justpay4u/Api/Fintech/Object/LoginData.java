package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 15-03-2018.
 */

public class LoginData {
    @SerializedName("operatorOptionals")
    @Expose
    public List<OperatorOptional> operatorOptionals = null;
    @SerializedName("stateID")
    @Expose
    public int stateID;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("isP")
    @Expose
    public boolean isP;

    @SerializedName("isPN")
    @Expose
    public boolean isPN;
    int wid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("loginTypeID")
    @Expose
    private int loginTypeID;
    @SerializedName("canDebit")
    @Expose
    private boolean canDebit;
    @SerializedName("lt")
    @Expose
    private String lt;
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("roleID")
    @Expose
    private int roleID;
    @SerializedName("slabID")
    @Expose
    private String slabID;
    @SerializedName("rankName")
    @Expose
    private String rankName;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("outletID")
    @Expose
    private int outletID;
    @SerializedName("outletName")
    @Expose
    private String outletName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("isDoubleFactor")
    @Expose
    private boolean isDoubleFactor;
    @SerializedName("isPinRequired")
    @Expose
    private boolean isPinRequired;
    @SerializedName("prefix")
    @Expose
    private String prefix;

    public int getWid() {
        return wid;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getAddress() {
        return address;
    }

    public String getLt() {
        return lt;
    }

    public boolean getIsDoubleFactor() {
        return isDoubleFactor;
    }

    public void setDoubleFactor(boolean doubleFactor) {
        isDoubleFactor = doubleFactor;
    }

    public boolean getIsPinRequired() {
        return isPinRequired;
    }

    public String getSession() {
        if (session != null && !session.isEmpty()) {
            return session;
        } else {
            return "";
        }
    }

    public boolean isCanDebit() {
        return canDebit;
    }

    public int getOutletID() {
        return outletID;
    }


    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getLoginTypeID() {
        return loginTypeID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getRoleName() {
        return roleName;
    }


    public int getRoleID() {
        return roleID;
    }


    public String getSlabID() {
        return slabID;
    }

    public String getRankName() {
        return rankName;
    }

    public List<OperatorOptional> getOperatorOptionals() {
        return operatorOptionals;
    }


    public int getStateID() {
        return stateID;
    }

    public String getState() {
        return state;
    }


    public String getPrefix() {
        return prefix;
    }
}
