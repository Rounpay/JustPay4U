package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.SlabDetailDisplayLvl;

import java.util.ArrayList;

public class SlabCommissionResponse {
    @SerializedName("rechargeReport")
    @Expose
    public Object rechargeReport;
    @SerializedName("dmtReport")
    @Expose
    public Object dmtReport;
    @SerializedName("ledgerReport")
    @Expose
    public Object ledgerReport;
    @SerializedName("fundDCReport")
    @Expose
    public Object fundDCReport;
    @SerializedName("fundOrderReport")
    @Expose
    public Object fundOrderReport;
    @SerializedName("slabCommissions")
    @Expose
    public Object slabCommissions;
    @SerializedName("slabDetailDisplayLvl")
    @Expose
    public ArrayList<SlabDetailDisplayLvl> slabDetailDisplayLvl = null;
    @SerializedName("userList")
    @Expose
    public Object userList;
    @SerializedName("childRoles")
    @Expose
    public Object childRoles;
    @SerializedName("fundRequestForApproval")
    @Expose
    public Object fundRequestForApproval;
    @SerializedName("newsContent")
    @Expose
    public Object newsContent;
    @SerializedName("banners")
    @Expose
    public Object banners;
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

    public Object getRechargeReport() {
        return rechargeReport;
    }

    public Object getDmtReport() {
        return dmtReport;
    }

    public Object getLedgerReport() {
        return ledgerReport;
    }

    public Object getFundDCReport() {
        return fundDCReport;
    }

    public Object getFundOrderReport() {
        return fundOrderReport;
    }

    public Object getSlabCommissions() {
        return slabCommissions;
    }

    public ArrayList<SlabDetailDisplayLvl> getSlabDetailDisplayLvl() {
        return slabDetailDisplayLvl;
    }

    public Object getUserList() {
        return userList;
    }

    public Object getChildRoles() {
        return childRoles;
    }

    public Object getFundRequestForApproval() {
        return fundRequestForApproval;
    }

    public Object getNewsContent() {
        return newsContent;
    }

    public Object getBanners() {
        return banners;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }
}
