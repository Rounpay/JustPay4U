package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitiateUpiResponse {
    @SerializedName(value = "QRIntent", alternate = {"qRIntent", "qrIntent", "qrintent"})
    @Expose
    public String QRIntent;
    @SerializedName("bankOrderID")
    @Expose
    public String bankOrderID;
    @SerializedName("mvpa")
    @Expose
    public String mvpa;
    @SerializedName("terminalID")
    @Expose
    public String terminalID;
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
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("sid")
    @Expose
    public String sid;
    @SerializedName("isOTPRequired")
    @Expose
    public boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    public boolean isResendAvailable;
    @SerializedName("getID")
    @Expose
    public int getID;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;

    public String getTid() {
        return tid;
    }

    public String getBankOrderID() {
        return bankOrderID;
    }

    public String getMvpa() {
        return mvpa;
    }

    public String getTerminalID() {
        return terminalID;
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

    public int getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getSid() {
        return sid;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public boolean isResendAvailable() {
        return isResendAvailable;
    }

    public String getQRIntent() {
        return QRIntent;
    }

    public int getGetID() {
        return getID;
    }
}
