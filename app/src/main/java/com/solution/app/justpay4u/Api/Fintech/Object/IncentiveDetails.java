package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 21,January,2020
 */
public class IncentiveDetails {
    @SerializedName("denomination")
    @Expose
    int denomination;
    @SerializedName("comm")
    @Expose
    double comm;
    @SerializedName("amtType")
    @Expose
    boolean amtType;

    public int getDenomination() {
        return denomination;
    }

    public double getComm() {
        return comm;
    }

    public boolean isAmtType() {
        return amtType;
    }
}
