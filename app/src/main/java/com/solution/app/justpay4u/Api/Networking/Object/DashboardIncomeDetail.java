package com.solution.app.justpay4u.Api.Networking.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 04/08/2022.
 */
public class DashboardIncomeDetail {
    @SerializedName("incomeOPID")
    @Expose
    public int incomeOPID;
    @SerializedName("incomeFigure")
    @Expose
    public double incomeFigure;
    @SerializedName("incomeName")
    @Expose
    public String incomeName;
    @SerializedName("incomeCategoryID")
    @Expose
    public int incomeCategoryID;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;

    public DashboardIncomeDetail(int incomeOPID, double incomeFigure, String incomeName, int incomeCategoryID, boolean isActive) {
        this.incomeOPID = incomeOPID;
        this.incomeFigure = incomeFigure;
        this.incomeName = incomeName;
        this.incomeCategoryID = incomeCategoryID;
        this.isActive = isActive;
    }

    public int getIncomeOPID() {
        return incomeOPID;
    }

    public double getIncomeFigure() {
        return incomeFigure;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public int getIncomeCategoryID() {
        return incomeCategoryID;
    }

    public boolean isActive() {
        return isActive;
    }
}
