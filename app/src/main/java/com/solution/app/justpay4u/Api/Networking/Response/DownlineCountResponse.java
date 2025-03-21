package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 09/02/2022.
 */
public class DownlineCountResponse {
    @SerializedName("directDownlinelUser")
    @Expose
    public int directDownlinelUser;
    @SerializedName("directDownlinelUserActive")
    @Expose
    public int directDownlinelUserActive;
    @SerializedName("directDownlinelUserDeactive")
    @Expose
    public int directDownlinelUserDeactive;
    @SerializedName("allDownlinelUser")
    @Expose
    public int allDownlinelUser;
    @SerializedName("allDownlinelUserActive")
    @Expose
    public int allDownlinelUserActive;
    @SerializedName("allDownlinelUserDeActive")
    @Expose
    public int allDownlinelUserDeActive;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("shareLink")
    @Expose
    public String shareLink;
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
    @SerializedName("pCode")
    @Expose
    public Object pCode;
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

    public String getShareLink() {
        return shareLink;
    }

    public int getDirectDownlinelUser() {
        return directDownlinelUser;
    }

    public int getDirectDownlinelUserActive() {
        return directDownlinelUserActive;
    }

    public int getDirectDownlinelUserDeactive() {
        return directDownlinelUserDeactive;
    }

    public int getAllDownlinelUser() {
        return allDownlinelUser;
    }

    public int getAllDownlinelUserActive() {
        return allDownlinelUserActive;
    }

    public int getAllDownlinelUserDeActive() {
        return allDownlinelUserDeActive;
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

    public Object getpCode() {
        return pCode;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public boolean isResendAvailable() {
        return isResendAvailable;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public boolean isRoffer() {
        return isRoffer;
    }

    public boolean isGreen() {
        return isGreen;
    }

    public boolean isBulkQRGeneration() {
        return isBulkQRGeneration;
    }

    public boolean isCoin() {
        return isCoin;
    }
}
