package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.MiniStatements;

import java.util.ArrayList;

public class GetAEPSResponse {


    @SerializedName(value = "Statements", alternate = "statements")
    @Expose
    public ArrayList<MiniStatements> statements;
    @SerializedName(value = "Balance", alternate = "balance")
    @Expose
    public double balance;
    @SerializedName(value = "Status", alternate = "status")
    @Expose
    public int status;
    @SerializedName(value = "LiveID", alternate = "liveID")
    @Expose
    public String liveID;
    @SerializedName(value = "TransactionID", alternate = "transactionID")
    @Expose
    public String transactionID;
    @SerializedName(value = "TransactionDate", alternate = "transactionDate")
    @Expose
    public String transactionDate;
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

    public double getBalance() {
        return balance;
    }

    public int getStatus() {
        return status;
    }

    public String getLiveID() {
        return liveID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public ArrayList<MiniStatements> getStatements() {
        return statements;
    }
}
