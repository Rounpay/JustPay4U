package com.solution.app.justpay4u.ApiHits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignedOpTypeIndustryWise {
    @SerializedName(value = "id",alternate = {"serviceID"})
    @Expose
    int id;
    @SerializedName(value = "parentID")
    @Expose
    int parentID;


    boolean isServiceActive;
    boolean isPaidAdditional;
    boolean isActive;

    boolean isAdditionalServiceType;
    


    public boolean isServiceActive() {
        return isServiceActive;
    }

    public boolean isPaidAdditional() {
        return isPaidAdditional;
    }


    public boolean isActive() {
        return isActive;
    }

    public boolean isAdditionalServiceType() {
        return isAdditionalServiceType;
    }

    public int getParentID() {
        return parentID;
    }

    @SerializedName(value = "opType",alternate = {"name"})
    @Expose
    String opType;
    @SerializedName("apiOpType")
    @Expose
    String apiOpType;
    @SerializedName("remark")
    @Expose
    String remark;
    @SerializedName("sCode")
    @Expose
    String sCode;
    @SerializedName("serviceTypeID")
    @Expose
    int serviceTypeID;
    @SerializedName("isB2CVisible")
    @Expose
    boolean isB2CVisible;

    public int getId() {
        return id;
    }

    public String getOpType() {
        return opType;
    }

    public String getApiOpType() {
        return apiOpType;
    }

    public String getRemark() {
        return remark;
    }

    public String getsCode() {
        return sCode;
    }

    public int getServiceTypeID() {
        return serviceTypeID;
    }

    public boolean isB2CVisible() {
        return isB2CVisible;
    }

}
