package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEmpTodayLivePST {
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("totalAmount")
    @Expose
    public double totalAmount;
    @SerializedName("totalUser")
    @Expose
    public double totalUser;
    @SerializedName("uniqueUser")
    @Expose
    public double uniqueUser;


    public String getType() {
        return type;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getTotalUser() {
        return totalUser;
    }

    public double getUniqueUser() {
        return uniqueUser;
    }
}
