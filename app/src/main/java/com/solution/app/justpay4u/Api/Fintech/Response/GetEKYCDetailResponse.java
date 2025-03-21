package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.EKycDetailData;

/**
 * Created by Vishnu Agarwal on 17/01/2022.
 */
public class GetEKYCDetailResponse {

    @SerializedName("data")
    @Expose
    private EKycDetailData data;
    @SerializedName("isSattlemntAccountVerify")
    @Expose
    private boolean isSattlemntAccountVerify;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName(value = "RID", alternate = {"rID", "rId", "rid", "reffID"})
    @Expose
    private String rid;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    private int checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    private Object mobileNo;
    @SerializedName("emailID")
    @Expose
    private Object emailID;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;
    @SerializedName("isDTHInfoCall")
    @Expose
    private boolean isDTHInfoCall;
    @SerializedName("isShowPDFPlan")
    @Expose
    private boolean isShowPDFPlan;
    @SerializedName("sid")
    @Expose
    private Object sid;
    @SerializedName("pCode")
    @Expose
    private Object pCode;
    @SerializedName("isOTPRequired")
    @Expose
    private boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    private boolean isResendAvailable;
    @SerializedName("isDTHInfo")
    @Expose
    private boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private boolean isRoffer;
    @SerializedName("isGreen")
    @Expose
    private boolean isGreen;
    @SerializedName("isBulkQRGeneration")
    @Expose
    private boolean isBulkQRGeneration;
    @SerializedName("isEKYCForced")
    @Expose
    private boolean isEKYCForced;
    @SerializedName("isCoin")
    @Expose
    private boolean isCoin;

    public EKycDetailData getData() {
        return data;
    }

    public boolean isIsSattlemntAccountVerify() {
        return isSattlemntAccountVerify;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isIsVersionValid() {
        return isVersionValid;
    }

    public boolean isIsAppValid() {
        return isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }

    public boolean isIsPasswordExpired() {
        return isPasswordExpired;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public Object getEmailID() {
        return emailID;
    }

    public boolean isIsLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public boolean isIsDTHInfoCall() {
        return isDTHInfoCall;
    }

    public String getRid() {
        return rid;
    }

    public boolean isIsShowPDFPlan() {
        return isShowPDFPlan;
    }

    public Object getSid() {
        return sid;
    }

    public Object getpCode() {
        return pCode;
    }

    public boolean isIsOTPRequired() {
        return isOTPRequired;
    }

    public boolean isIsResendAvailable() {
        return isResendAvailable;
    }

    public boolean isIsDTHInfo() {
        return isDTHInfo;
    }

    public boolean isIsRoffer() {
        return isRoffer;
    }

    public boolean isIsGreen() {
        return isGreen;
    }

    public boolean isIsBulkQRGeneration() {
        return isBulkQRGeneration;
    }

    public boolean isIsEKYCForced() {
        return isEKYCForced;
    }

    public boolean isIsCoin() {
        return isCoin;
    }

}
