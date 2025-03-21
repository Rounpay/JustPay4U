package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PackageDetails {
    @SerializedName("serviceChargeDeuctionType")
    @Expose
    int serviceChargeDeuctionType;
    @SerializedName("outletID")
    @Expose
    int outletID;
    @SerializedName("uid")
    @Expose
    int uid;
    @SerializedName("packageId")
    @Expose
    int packageId;
    @SerializedName("opTypeID")
    @Expose
    int opTypeID;
    @SerializedName("oid")
    @Expose
    int oid;
    @SerializedName(value = "packageName", alternate = "displayName")
    @Expose
    String packageName;

    @SerializedName("idLimit")
    @Expose
    double idLimit;
    @SerializedName(value = "packageCost", alternate = "charges")
    @Expose
    double packageCost;
    @SerializedName("commission")
    @Expose
    double commission;
    @SerializedName("isDefault")
    @Expose
    boolean isDefault;
    @SerializedName("isActive")
    @Expose
    boolean isActive;
    @SerializedName("isIDLimitByOpertor")
    @Expose
    boolean isIDLimitByOpertor;
    ArrayList<PackageService> services;

    public int getPackageId() {
        return packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public double getPackageCost() {
        return packageCost;
    }

    public double getCommission() {
        return commission;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public ArrayList<PackageService> getServices() {
        return services;
    }

    public int getServiceChargeDeuctionType() {
        return serviceChargeDeuctionType;
    }

    public int getOutletID() {
        return outletID;
    }

    public int getUid() {
        return uid;
    }

    public int getOpTypeID() {
        return opTypeID;
    }

    public int getOid() {
        return oid;
    }

    public double getIdLimit() {
        return idLimit;
    }

    public boolean isIDLimitByOpertor() {
        return isIDLimitByOpertor;
    }
}
