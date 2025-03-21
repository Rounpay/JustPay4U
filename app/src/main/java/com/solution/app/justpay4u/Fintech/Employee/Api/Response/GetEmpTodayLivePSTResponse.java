package com.solution.app.justpay4u.Fintech.Employee.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Fintech.Employee.Api.Object.GetEmpTodayLivePST;

import java.util.List;

public class GetEmpTodayLivePSTResponse {
    @SerializedName("data")
    @Expose
    public List<GetEmpTodayLivePST> data = null;
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
    public Object mobileNo;
    @SerializedName("emailID")
    @Expose
    public Object emailID;
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
    public Object sid;
    @SerializedName("isOTPRequired")
    @Expose
    public boolean isOTPRequired;
    @SerializedName("getID")
    @Expose
    public int getID;
    @SerializedName("isDTHInfo")
    @Expose
    public boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    public boolean isRoffer;

    public List<GetEmpTodayLivePST> getData() {
        return data;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public Object getEmailID() {
        return emailID;
    }

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

    public boolean isShowPDFPlan() {
        return isShowPDFPlan;
    }

    public Object getSid() {
        return sid;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public int getGetID() {
        return getID;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public boolean isRoffer() {
        return isRoffer;
    }
}
