package com.solution.app.justpay4u.Api.Networking.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 03/02/2022.
 */
public class PlanPackage {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("slab")
    @Expose
    public String slab;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("packageCost")
    @Expose
    public double packageCost;
    @SerializedName("isTopup")
    @Expose
    public int isTopup;

    public int getId() {
        return id;
    }

    public String getSlab() {
        return slab;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getRemark() {
        return remark;
    }

    public double getPackageCost() {
        return packageCost;
    }

    public int getIsTopup() {
        return isTopup;
    }
}
