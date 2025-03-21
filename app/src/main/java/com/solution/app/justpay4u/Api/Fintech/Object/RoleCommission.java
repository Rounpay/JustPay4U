package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoleCommission {
    @SerializedName("roleID")
    @Expose
    public int roleID;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("comm")
    @Expose
    public double comm;
    @SerializedName("commType")
    @Expose
    public int commType;
    @SerializedName("amtType")
    @Expose
    public int amtType;
    @SerializedName("modifyDate")
    @Expose
    public String modifyDate;

    @SerializedName("maxComm")
    @Expose
    double maxComm;
    @SerializedName("rComm")
    @Expose
    double rComm;
    @SerializedName("rMaxComm")
    @Expose
    double rMaxComm;
    @SerializedName("rCommType")
    @Expose
    int rCommType;
    @SerializedName("rAmtType")
    @Expose
    int rAmtType;

    public double getMaxComm() {
        return maxComm;
    }

    public double getrComm() {
        return rComm;
    }

    public double getrMaxComm() {
        return rMaxComm;
    }

    public int getrCommType() {
        return rCommType;
    }

    public int getrAmtType() {
        return rAmtType;
    }

    public int getRoleID() {
        return roleID;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRole() {
        return role;
    }

    public double getComm() {
        return comm;
    }

    public int getCommType() {
        return commType;
    }

    public int getAmtType() {
        return amtType;
    }

    public String getModifyDate() {
        return modifyDate;
    }
}
