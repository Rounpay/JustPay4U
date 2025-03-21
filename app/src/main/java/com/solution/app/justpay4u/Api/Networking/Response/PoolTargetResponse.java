package com.solution.app.justpay4u.Api.Networking.Response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Networking.Object.PoolTargetData;

import java.util.ArrayList;

public class PoolTargetResponse {
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("data")
    @Expose
    public ArrayList<PoolTargetData> data;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public ArrayList<PoolTargetData> getData() {
        return data;
    }
}

