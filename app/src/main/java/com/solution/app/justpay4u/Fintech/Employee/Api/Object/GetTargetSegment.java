package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTargetSegment {
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("target")
    @Expose
    public double target;
    @SerializedName("achieve")
    @Expose
    public double achieve;
    @SerializedName("achievePercent")
    @Expose
    public double achievePercent;
    @SerializedName("incentive")
    @Expose
    public double incentive;

    public String getType() {
        return type;
    }

    public double getTarget() {
        return target;
    }

    public double getAchieve() {
        return achieve;
    }

    public double getAchievePercent() {
        return achievePercent;
    }

    public double getIncentive() {
        return incentive;
    }
}
