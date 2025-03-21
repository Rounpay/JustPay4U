package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.PlanData;

public class PlanResponse {

    @SerializedName("data")
    @Expose
    private PlanData data;

    @SerializedName("dataRP")
    @Expose
    private PlanData dataRP;

    @SerializedName("dataPA")
    @Expose
    private PlanData dataPA;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;

    public PlanData getData() {
        return data;
    }

    public PlanData getDataRP() {
        return dataRP;
    }

    public PlanData getDataPA() {
        return dataPA;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }
}
