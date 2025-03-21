package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateMeatingResaon {
    @SerializedName("reasonID")
    @Expose
    public int reasonID;
    @SerializedName("reason")
    @Expose
    public String reason;

    public int getReasonID() {
        return reasonID;
    }

    public String getReason() {
        return reason;
    }
}
