package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatorOptional {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("oid")
    @Expose
    public int oid;
    @SerializedName("optionalType")
    @Expose
    public int optionalType;
    @SerializedName("displayName")
    @Expose
    public String displayName;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("isList")
    @Expose
    public boolean isList;
    @SerializedName("isMultiSelection")
    @Expose
    public boolean isMultiSelection;

    public int getId() {
        return id;
    }

    public int getOid() {
        return oid;
    }

    public int getOptionalType() {
        return optionalType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRemark() {
        return remark;
    }

    public boolean getList() {
        return isList;
    }

    public boolean getMultiSelection() {
        return isMultiSelection;
    }
}
