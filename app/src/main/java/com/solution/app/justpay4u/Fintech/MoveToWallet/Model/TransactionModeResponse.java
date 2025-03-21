package com.solution.app.justpay4u.Fintech.MoveToWallet.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TransactionModeResponse {

    @SerializedName("statuscode")
    @Expose
    private String statuscode;
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
    private String checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;
    @SerializedName("transactionModes")
    @Expose
    private ArrayList<TransactionMode> transactionModes = null;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
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

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public void setIsPasswordExpired(boolean isPasswordExpired) {
        this.isPasswordExpired = isPasswordExpired;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public boolean getIsLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public void setIsLookUpFromAPI(boolean isLookUpFromAPI) {
        this.isLookUpFromAPI = isLookUpFromAPI;
    }

    public ArrayList<TransactionMode> getTransactionModes() {
        return transactionModes;
    }

    public void setTransactionModes(ArrayList<TransactionMode> transactionModes) {
        this.transactionModes = transactionModes;
    }
}
