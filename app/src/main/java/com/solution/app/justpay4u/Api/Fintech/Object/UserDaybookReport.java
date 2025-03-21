package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDaybookReport {
    @SerializedName("api")
    @Expose
    public String api;
    @SerializedName("oid")
    @Expose
    public String oid;
    @SerializedName("operator")
    @Expose
    public String operator;
    @SerializedName("totalHits")
    @Expose
    public int totalHits;
    @SerializedName("totalAmount")
    @Expose
    public double totalAmount;
    @SerializedName("successHits")
    @Expose
    public int successHits;
    @SerializedName("successAmount")
    @Expose
    public double successAmount;
    @SerializedName("failedHits")
    @Expose
    public int failedHits;
    @SerializedName("failedAmount")
    @Expose
    public double failedAmount;
    @SerializedName("pendingHits")
    @Expose
    public int pendingHits;
    @SerializedName("pendingAmount")
    @Expose
    public double pendingAmount;
    @SerializedName("apiCommission")
    @Expose
    public double apiCommission;
    @SerializedName("commission")
    @Expose
    public double commission;
    @SerializedName("selfCommission")
    @Expose
    public double selfCommission;

    @SerializedName("teamCommission")
    @Expose
    public double teamCommission;
    @SerializedName("profit")
    @Expose
    public double profit;
    @SerializedName("gstTaxAmount")
    @Expose
    public double gstTaxAmount;
    @SerializedName("tdsAmount")
    @Expose
    public double tdsAmount;
    @SerializedName("tDate")
    @Expose
    public String tDate;

    public String getApi() {
        return api;
    }

    public String getOid() {
        return oid;
    }

    public String getOperator() {
        return operator;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getSuccessHits() {
        return successHits;
    }

    public double getSuccessAmount() {
        return successAmount;
    }

    public int getFailedHits() {
        return failedHits;
    }

    public double getFailedAmount() {
        return failedAmount;
    }

    public int getPendingHits() {
        return pendingHits;
    }

    public double getPendingAmount() {
        return pendingAmount;
    }

    public double getApiCommission() {
        return apiCommission;
    }

    public double getCommission() {
        return commission;
    }

    public double getSelfCommission() {
        return selfCommission;
    }

    public double getTeamCommission() {
        return teamCommission;
    }

    public double getProfit() {
        return profit;
    }

    public double getGstTaxAmount() {
        return gstTaxAmount;
    }

    public double getTdsAmount() {
        return tdsAmount;
    }

    public String gettDate() {
        return tDate;
    }
}
