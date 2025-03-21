package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLastdayVsTodayChart {
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("lastDay")
    @Expose
    public double lastDay;
    @SerializedName("today")
    @Expose
    public double today;


    public String getType() {
        return type;
    }

    public double getLastDay() {
        return lastDay;
    }

    public double getToday() {
        return today;
    }
}
