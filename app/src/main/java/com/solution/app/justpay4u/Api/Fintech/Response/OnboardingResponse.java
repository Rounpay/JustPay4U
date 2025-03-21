package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.SDKDetail;

import java.util.List;

public class OnboardingResponse {
    String oId;
    @SerializedName("otpRefID")
    @Expose
    private String otpRefID;
    @SerializedName("giurl")
    @Expose
    private String giurl;
    @SerializedName("isConfirmation")
    @Expose
    private boolean isConfirmation;
    @SerializedName("isRedirection")
    @Expose
    private boolean isRedirection;
    @SerializedName("isOTPRequired")
    @Expose
    private boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    private boolean isResendAvailable;
    @SerializedName("isBioMetricRequired")
    @Expose
    private boolean isBioMetricRequired;
    @SerializedName("isRedirectToExternal")
    @Expose
    private boolean isRedirectToExternal;
    @SerializedName("externalURL")
    @Expose
    private String externalURL;
    @SerializedName("isDown")
    @Expose
    private boolean isDown;
    @SerializedName("isWaiting")
    @Expose
    private boolean isWaiting;
    @SerializedName("isRejected")
    @Expose
    private boolean isRejected;
    @SerializedName("isIncomplete")
    @Expose
    private boolean isIncomplete;
    @SerializedName("isUnathorized")
    @Expose
    private boolean isUnathorized;
    @SerializedName("bcResponse")
    @Expose
    private List<BcResponse> bcResponse = null;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("bioAuthType")
    @Expose
    private int bioAuthType;
    @SerializedName("msg")
    @Expose
    private String msg;
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
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;

    @SerializedName("panid")
    @Expose
    private String panid;

    @SerializedName("isShowMsg")
    @Expose
    private boolean isShowMsg;
    @SerializedName("sdkType")
    @Expose
    private int sdkType;
    @SerializedName("sdkDetail")
    @Expose
    private SDKDetail sdkDetail;
    @SerializedName("inInterface")
    @Expose
    private boolean inInterface;
    @SerializedName("interfaceType")
    @Expose
    private int interfaceType;
    @SerializedName("redirectURL")
    @Expose
    private String redirectURL;
    public boolean isShowMsg() {
        return isShowMsg;
    }

    public void setShowMsg(boolean showMsg) {
        isShowMsg = showMsg;
    }

    public String getOtpRefID() {
        return otpRefID;
    }
//isShowMsg

    public String getRedirectURL() {
        return redirectURL;
    }

    public String getPanid() {
        return panid;
    }

    public void setPanid(String panid) {
        this.panid = panid;
    }

    public int getSdkType() {
        return sdkType;
    }

    public SDKDetail getSdkDetail() {
        return sdkDetail;
    }

//panid

    public boolean getIsConfirmation() {
        return isConfirmation;
    }

    public void setIsConfirmation(boolean isConfirmation) {
        this.isConfirmation = isConfirmation;
    }

    public boolean getIsRedirection() {
        return isRedirection;
    }

    public void setIsRedirection(boolean isRedirection) {
        this.isRedirection = isRedirection;
    }

    public boolean isBioMetricRequired() {
        return isBioMetricRequired;
    }

    public int getBioAuthType() {
        return bioAuthType;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public boolean getIsDown() {
        return isDown;
    }

    public void setIsDown(boolean isDown) {
        this.isDown = isDown;
    }

    public boolean getIsWaiting() {
        return isWaiting;
    }

    public void setIsWaiting(boolean isWaiting) {
        this.isWaiting = isWaiting;
    }

    public boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(boolean isRejected) {
        this.isRejected = isRejected;
    }

    public boolean getIsIncomplete() {
        return isIncomplete;
    }

    public void setIsIncomplete(boolean isIncomplete) {
        this.isIncomplete = isIncomplete;
    }

    public boolean getIsUnathorized() {
        return isUnathorized;
    }

    public void setIsUnathorized(boolean isUnathorized) {
        this.isUnathorized = isUnathorized;
    }

    public List<BcResponse> getBcResponse() {
        return bcResponse;
    }

    public void setBcResponse(List<BcResponse> bcResponse) {
        this.bcResponse = bcResponse;
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

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public void setIsVersionValid(boolean isVersionValid) {
        this.isVersionValid = isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public void setIsAppValid(boolean isAppValid) {
        this.isAppValid = isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }

    public void setCheckID(int checkID) {
        this.checkID = checkID;
    }

    public boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public void setIsPasswordExpired(boolean isPasswordExpired) {
        this.isPasswordExpired = isPasswordExpired;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
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

    public boolean isResendAvailable() {
        return isResendAvailable;
    }

    public boolean isRedirectToExternal() {
        return isRedirectToExternal;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public boolean isInInterface() {
        return inInterface;
    }

    public int getInterfaceType() {
        return interfaceType;
    }

    public String getGiurl() {
        return giurl;
    }
}
