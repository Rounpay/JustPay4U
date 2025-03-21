package com.solution.app.justpay4u.Networking.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletCryptoReport {
    @SerializedName("status")
    @Expose
    int status;
    @SerializedName("tid")
    @Expose
    String tid;
    @SerializedName("userId")
    @Expose
    String userId;
    @SerializedName("userName")
    @Expose
    String userName;
    @SerializedName("requestedAmount")
    @Expose
    double requestedAmount;
    @SerializedName("fromCurrencyName")
    @Expose
    String fromCurrencyName;
    @SerializedName("toCurrencyName")
    @Expose
    String toCurrencyName;
    @SerializedName("toWallet")
    @Expose
    String toWallet;
    @SerializedName("toAddress")
    @Expose
    String toAddress;
    @SerializedName("convsertionRate")
    @Expose
    double convsertionRate;
    @SerializedName("transferAmount")
    @Expose
    double transferAmount;
    @SerializedName("liveRate")
    @Expose
    double liveRate;
    @SerializedName("liveId")
    @Expose
    String liveId;
    @SerializedName("entryDate")
    @Expose
    String entryDate;

    public int getStatus() {
        return status;
    }

    public String getTid() {
        return tid;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public String getFromCurrencyName() {
        return fromCurrencyName;
    }

    public String getToCurrencyName() {
        return toCurrencyName;
    }

    public String getToWallet() {
        return toWallet;
    }

    public String getToAddress() {
        return toAddress;
    }

    public double getConvsertionRate() {
        return convsertionRate;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public String getLiveId() {
        return liveId;
    }

    public double getLiveRate() {
        return liveRate;
    }

    public String getEntryDate() {
        return entryDate;
    }
}
