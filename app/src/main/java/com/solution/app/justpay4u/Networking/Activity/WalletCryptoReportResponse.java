package com.solution.app.justpay4u.Networking.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WalletCryptoReportResponse {

    @SerializedName("statuscode")
    @Expose
    int statuscode;
    @SerializedName("msg")
    @Expose
    String msg;
    @SerializedName("isVersionValid")
    @Expose
    boolean isVersionValid;

    @SerializedName(value = "withdrawalWalletReport",alternate = "withdrawalCryptoReport")
    @Expose
    ArrayList<WalletCryptoReport> report;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public ArrayList<WalletCryptoReport> getReport() {
        return report;
    }
}
