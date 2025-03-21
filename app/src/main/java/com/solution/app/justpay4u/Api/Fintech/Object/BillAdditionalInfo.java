package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillAdditionalInfo {

    @SerializedName("infoName")
    @Expose
    public String infoName;
    @SerializedName("infoValue")
    @Expose
    public String infoValue;
    @SerializedName("amountName")
    @Expose
    public String amountName;
    @SerializedName("amountValue")
    @Expose
    public double amountValue;

    public String getAmountName() {
        return amountName;
    }

    public double getAmountValue() {
        return amountValue;
    }

    public String getInfoName() {
        return infoName;
    }

    public String getInfoValue() {
        return infoValue;
    }
}
