package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlanTypes {
    @SerializedName("pType")
    @Expose
    private String pType;
    @SerializedName("pDetails")
    @Expose
    private ArrayList<PlanDataDetails> pDetails;

    public String getpType() {
        return pType;
    }

    public ArrayList<PlanDataDetails> getpDetails() {
        return pDetails;
    }
}
