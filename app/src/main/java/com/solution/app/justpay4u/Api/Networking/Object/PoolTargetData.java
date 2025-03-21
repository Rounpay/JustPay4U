package com.solution.app.justpay4u.Api.Networking.Object;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoolTargetData {
    @SerializedName("poolId")
    @Expose
    private Integer poolId;
    @SerializedName("poolName")
    @Expose
    private String poolName;
    @SerializedName("poolMatrix")
    @Expose
    private Integer poolMatrix;
    @SerializedName("maxLevel")
    @Expose
    private Integer maxLevel;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("requiredMember")
    @Expose
    private Integer requiredMember;
    @SerializedName("completedMember")
    @Expose
    private Integer completedMember;
    @SerializedName("remainingMember")
    @Expose
    private Integer remainingMember;
    @SerializedName("entryStatus")
    @Expose
    private Boolean entryStatus;
    @SerializedName("displayFields")
    @Expose
    private String displayFields;
    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;

    public Integer getPoolId() {
        return poolId;
    }

    public String getPoolName() {
        return poolName;
    }

    public Integer getPoolMatrix() {
        return poolMatrix;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public Integer getRequiredMember() {
        return requiredMember;
    }

    public Integer getCompletedMember() {
        return completedMember;
    }

    public Integer getRemainingMember() {
        return remainingMember;
    }

    public Boolean getEntryStatus() {
        return entryStatus;
    }

    public String getDisplayFields() {
        return displayFields;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
