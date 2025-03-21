package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletType {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;
    @SerializedName("inFundProcess")
    @Expose
    public boolean inFundProcess;
    @SerializedName("isPackageDedectionForRetailor")
    @Expose
    public boolean isPackageDedectionForRetailor;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getActive() {
        return isActive;
    }

    public boolean getInFundProcess() {
        return inFundProcess;
    }

    public boolean isPackageDedectionForRetailor() {
        return isPackageDedectionForRetailor;
    }
}
