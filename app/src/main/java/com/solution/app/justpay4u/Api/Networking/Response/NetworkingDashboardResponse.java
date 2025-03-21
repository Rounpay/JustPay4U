package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Networking.Object.NetworkingDashboardData;

/**
 * Created by Vishnu Agarwal on 04/08/2022.
 */
public class NetworkingDashboardResponse {
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
    public NetworkingDashboardData data;

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

    public NetworkingDashboardData getData() {
        return data;
    }
}
