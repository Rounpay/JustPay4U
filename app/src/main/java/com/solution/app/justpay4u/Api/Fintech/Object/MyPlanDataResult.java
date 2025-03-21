package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyPlanDataResult {
    @SerializedName("status")
    @Expose
    int status;
    @SerializedName("records")
    @Expose
    private PlanInfoRecords records;

    public int getStatus() {
        return status;
    }

    public PlanInfoRecords getRecords() {
        return records;
    }
}
