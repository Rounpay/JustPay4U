package com.solution.app.justpay4u.Api.Networking.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 26/08/2022.
 */
public class GetTopupDetailsByUserIdData {
    @SerializedName("oid")
    @Expose
    int oid;
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("packageId")
    @Expose
    int packageId;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("packageName")
    @Expose
    String packageName;
    @SerializedName("packageCost")
    @Expose
    double packageCost;
    @SerializedName("userBalance")
    @Expose
    double userBalance;
    @SerializedName("walletTypeList")
    @Expose
    ArrayList<GetTopupDetailsByUserIdData> walletTypeList;
    @SerializedName("packageList")
    @Expose
    ArrayList<GetTopupDetailsByUserIdData> packageList;

    public int getOid() {
        return oid;
    }

    public int getId() {
        return id;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public double getPackageCost() {
        return packageCost;
    }

    public double getUserBalance() {
        return userBalance;
    }

    public ArrayList<GetTopupDetailsByUserIdData> getWalletTypeList() {
        return walletTypeList;
    }

    public ArrayList<GetTopupDetailsByUserIdData> getPackageList() {
        return packageList;
    }
}
