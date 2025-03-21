package com.solution.app.justpay4u.ApiHits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPoolCount {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("level")
    @Expose
    private int level;
    @SerializedName("activeCount")
    @Expose
    private int activeCount;
    @SerializedName("deActiveCount")
    @Expose
    private int deActiveCount;
    @SerializedName("isMultiLayerTeamDisplay")
    @Expose
    private boolean isMultiLayerTeamDisplay;
    @SerializedName("maxReportDisplayLevel")
    @Expose
    private int maxReportDisplayLevel;


    public String getStatus() {
        return status;
    }


    public int getCount() {
        return count;
    }


    public int getLevel() {
        return level;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public int getDeActiveCount() {
        return deActiveCount;
    }

    public boolean isMultiLayerTeamDisplay() {
        return isMultiLayerTeamDisplay;
    }

    public int getMaxReportDisplayLevel() {
        return maxReportDisplayLevel;
    }
}
