package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLMTDVsMTD {
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("lm")
    @Expose
    public double lm;
    @SerializedName("lmtd")
    @Expose
    public double lmtd;
    @SerializedName("mtd")
    @Expose
    public double mtd;
    @SerializedName("growth")
    @Expose
    public double growth;

    public String getType() {
        return type;
    }

    public double getLm() {
        return lm;
    }

    public double getLmtd() {
        return lmtd;
    }

    public double getMtd() {
        return mtd;
    }

    public double getGrowth() {
        return growth;
    }
}
