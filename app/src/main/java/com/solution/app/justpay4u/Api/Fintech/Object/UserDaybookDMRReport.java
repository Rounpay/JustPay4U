package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDaybookDMRReport {
    @SerializedName("totalHits")
    @Expose
    public int totalHits;
    @SerializedName("totalAmount")
    @Expose
    public double totalAmount;
    @SerializedName("ccf")
    @Expose
    public double ccf;
    @SerializedName("baseAmount")
    @Expose
    public double baseAmount;
    @SerializedName("surcharge")
    @Expose
    public double surcharge;
    @SerializedName("gstOnSurcharge")
    @Expose
    public double gstOnSurcharge;
    @SerializedName("amountAfterSurcharge")
    @Expose
    public double amountAfterSurcharge;
    @SerializedName("refundGST")
    @Expose
    public double refundGST;
    @SerializedName("amountWithTDS")
    @Expose
    public double amountWithTDS;
    @SerializedName("tds")
    @Expose
    public double tds;
    @SerializedName("creditedAmount")
    @Expose
    public double creditedAmount;
    @SerializedName("apiSurcharge")
    @Expose
    public double apiSurcharge;
    @SerializedName("apigstOnSurcharge")
    @Expose
    public double apigstOnSurcharge;
    @SerializedName("apiAmountAfterSurcharge")
    @Expose
    public double apiAmountAfterSurcharge;
    @SerializedName("apiRefundGST")
    @Expose
    public double apiRefundGST;
    @SerializedName("apiAmountWithTDS")
    @Expose
    public double apiAmountWithTDS;
    @SerializedName("apitds")
    @Expose
    public double apitds;
    @SerializedName("apiCreditedAmount")
    @Expose
    public double apiCreditedAmount;
    @SerializedName("selfCommission")
    @Expose
    public double selfCommission;
    @SerializedName("commission")
    @Expose
    public double commission;
    @SerializedName("teamCommission")
    @Expose
    public double teamCommission;
    @SerializedName("profit")
    @Expose
    public double profit;

    public int getTotalHits() {
        return totalHits;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getCcf() {
        return ccf;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public double getGstOnSurcharge() {
        return gstOnSurcharge;
    }

    public double getAmountAfterSurcharge() {
        return amountAfterSurcharge;
    }

    public double getRefundGST() {
        return refundGST;
    }

    public double getAmountWithTDS() {
        return amountWithTDS;
    }

    public double getTds() {
        return tds;
    }

    public double getCreditedAmount() {
        return creditedAmount;
    }

    public double getApiSurcharge() {
        return apiSurcharge;
    }

    public double getApigstOnSurcharge() {
        return apigstOnSurcharge;
    }

    public double getApiAmountAfterSurcharge() {
        return apiAmountAfterSurcharge;
    }

    public double getApiRefundGST() {
        return apiRefundGST;
    }

    public double getApiAmountWithTDS() {
        return apiAmountWithTDS;
    }

    public double getApitds() {
        return apitds;
    }

    public double getApiCreditedAmount() {
        return apiCreditedAmount;
    }

    public double getSelfCommission() {
        return selfCommission;
    }

    public double getCommission() {
        return commission;
    }

    public double getTeamCommission() {
        return teamCommission;
    }

    public double getProfit() {
        return profit;
    }
}
