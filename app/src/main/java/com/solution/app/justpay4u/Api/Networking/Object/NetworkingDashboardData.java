package com.solution.app.justpay4u.Api.Networking.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Networking.Activity.DashboardTeamDetailsList;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 04/08/2022.
 */
public class NetworkingDashboardData {
    @SerializedName("singleData")
    @Expose
    public DashboardDownlineData singleData;
    @SerializedName("activeDeactive")
    @Expose
    public Object activeDeactive = null;
    @SerializedName("couponStatus")
    @Expose
    public Object couponStatus;
    @SerializedName("isTeamByPool")
    @Expose
    public boolean isTeamByPool;
    @SerializedName("teamDetailsList")
    @Expose
    public ArrayList<DashboardTeamDetailsList> teamDetailsList = null;

    public boolean isTeamByPool() {
        return isTeamByPool;
    }

    @SerializedName("regToupDatewise")
    @Expose
    public Object regToupDatewise;
    @SerializedName("incomeDetails")
    @Expose
    public ArrayList<DashboardIncomeDetail> incomeDetails = null;
    @SerializedName("dynamicTeamDisplay")
    @Expose
    public ArrayList<DashboardTeamDetails> teamDetails = null;

    public DashboardDownlineData getSingleData() {
        return singleData;
    }

    public Object getActiveDeactive() {
        return activeDeactive;
    }

    public Object getCouponStatus() {
        return couponStatus;
    }

    public Object getRegToupDatewise() {
        return regToupDatewise;
    }

    public ArrayList<DashboardIncomeDetail> getIncomeDetails() {
        return incomeDetails;
    }

    public ArrayList<DashboardTeamDetails> getTeamDetails() {
        return teamDetails;
    }
    public ArrayList<DashboardTeamDetailsList> getTeamDetailsList() {
        return teamDetailsList;
    }
}
