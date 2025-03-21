package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Networking.Object.PlanPackage;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 03/02/2022.
 */
public class PackagePlanResponse {
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("slabs")
    @Expose
    ArrayList<PlanPackage> slabs;

    public int getStatuscode() {
        return statuscode;
    }

    public String getName() {
        return name;
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

    public ArrayList<PlanPackage> getSlabs() {
        return slabs;
    }
}
