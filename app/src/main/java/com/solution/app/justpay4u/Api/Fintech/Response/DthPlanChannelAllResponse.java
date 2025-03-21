package com.solution.app.justpay4u.Api.Fintech.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Fintech.Object.DthPlanChannels;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 12/01/2022.
 */
public class DthPlanChannelAllResponse {
    @SerializedName("data")
    @Expose
    ArrayList<DthPlanChannels> data;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private String isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private String isAppValid;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public ArrayList<DthPlanChannels> getData() {
        return data;
    }
}
