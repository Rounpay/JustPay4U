package com.solution.app.justpay4u.ApiHits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Networking.Object.NetworkingDashboardData;

import java.util.ArrayList;
import java.util.List;

public class RankListResponse {
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
    @SerializedName("isTeamByPool")
    @Expose
    public boolean isTeamByPool;
    @SerializedName("data")
    @Expose

    public ArrayList<RankList> data;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }
    public boolean isTeamByPool() {
        return isTeamByPool;
    }

    public ArrayList<RankList> getData() {
        return data;
    }

}
