package com.solution.app.justpay4u.Api.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Networking.Object.IncomeWiseReport;

import java.util.ArrayList;
public class GetIncomeWiseReportResponse {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("incomeWiseReport")
    @Expose
    private ArrayList<IncomeWiseReport> incomeWiseReport;
    @SerializedName("totalIncomeAmount")
    @Expose
    private double totalIncomeAmount;
    @SerializedName("totalCreditedAmount")
    @Expose
    private double totalCreditedAmount;

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public void setIsVersionValid(boolean isVersionValid) {
        this.isVersionValid = isVersionValid;
    }

    public ArrayList<IncomeWiseReport> getIncomeWiseReport() {
        return incomeWiseReport;
    }

    public void setIncomeWiseReport(ArrayList<IncomeWiseReport> incomeWiseReport) {
        this.incomeWiseReport = incomeWiseReport;
    }

    public double getTotalIncomeAmount() {
        return totalIncomeAmount;
    }

    public void setTotalIncomeAmount(double totalIncomeAmount) {
        this.totalIncomeAmount = totalIncomeAmount;
    }

    public double getTotalCreditedAmount() {
        return totalCreditedAmount;
    }

    public void setTotalCreditedAmount(double totalCreditedAmount) {
        this.totalCreditedAmount = totalCreditedAmount;
    }

}