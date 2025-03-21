package com.solution.app.justpay4u.ApiHits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OpTypeDataIndustryWise {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("industryType")
    @Expose
    private String industryType;

    @SerializedName("remark")
    @Expose
    private String remark;

    @SerializedName("opTypes")
    @Expose
    private ArrayList<AssignedOpTypeIndustryWise> opTypes;

    public int getId() {
        return id;
    }

    public String getIndustryType() {
        return industryType;
    }

    public String getRemark() {
        return remark;
    }

    public ArrayList<AssignedOpTypeIndustryWise> getOpTypes() {
        return opTypes;
    }
}
