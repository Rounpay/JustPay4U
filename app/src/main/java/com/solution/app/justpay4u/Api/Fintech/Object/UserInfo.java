package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("isRealAPI")
    @Expose
    public boolean isRealAPI;
    @SerializedName("resultCode")
    @Expose
    public int resultCode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("outletName")
    @Expose
    public String outletName;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("roleID")
    @Expose
    public int roleID;
    @SerializedName("role")
    @Expose
    public Object role;
    @SerializedName("referalID")
    @Expose
    public int referalID;
    @SerializedName("slabID")
    @Expose
    public int slabID;
    @SerializedName("isGSTApplicable")
    @Expose
    public boolean isGSTApplicable;
    @SerializedName("isTDSApplicable")
    @Expose
    public boolean isTDSApplicable;
    @SerializedName("isVirtual")
    @Expose
    public boolean isVirtual;
    @SerializedName("isWebsite")
    @Expose
    public boolean isWebsite;
    @SerializedName("isAdminDefined")
    @Expose
    public boolean isAdminDefined;
    @SerializedName("isSurchargeGST")
    @Expose
    public boolean isSurchargeGST;
    @SerializedName("prefix")
    @Expose
    public Object prefix;
    @SerializedName("outletID")
    @Expose
    public int outletID;
    @SerializedName("pincode")
    @Expose
    public Object pincode;

    public boolean getRealAPI() {
        return isRealAPI;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public int getUserID() {
        return userID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getName() {
        return name;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getEmailID() {
        return emailID;
    }

    public int getRoleID() {
        return roleID;
    }

    public Object getRole() {
        return role;
    }

    public int getReferalID() {
        return referalID;
    }

    public int getSlabID() {
        return slabID;
    }

    public boolean getGSTApplicable() {
        return isGSTApplicable;
    }

    public boolean getTDSApplicable() {
        return isTDSApplicable;
    }

    public boolean getVirtual() {
        return isVirtual;
    }

    public boolean getWebsite() {
        return isWebsite;
    }

    public boolean getAdminDefined() {
        return isAdminDefined;
    }

    public boolean getSurchargeGST() {
        return isSurchargeGST;
    }

    public Object getPrefix() {
        return prefix;
    }

    public int getOutletID() {
        return outletID;
    }

    public Object getPincode() {
        return pincode;
    }
}
