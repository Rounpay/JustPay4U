package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SlabDetailDisplayLvl {

    @SerializedName("min")
    @Expose
    public int min;

    @SerializedName("max")
    @Expose
    public int max;
    @SerializedName("commSettingType")
    @Expose
    public int commSettingType;

    @SerializedName("oid")
    @Expose
    public int oid;
    @SerializedName("operator")
    @Expose
    public String operator;
    @SerializedName("opType")
    @Expose
    public String opType;
    @SerializedName(value = "opTypeID", alternate = "opTypeId")
    @Expose
    public int opTypeId;
    @SerializedName("roleCommission")
    @Expose
    public ArrayList<RoleCommission> roleCommission = null;
    String Header;

    public SlabDetailDisplayLvl(String header, int oid, String operator, int opTypeId, String opType, int min, int max, int commSettingType, ArrayList<RoleCommission> roleCommission) {
        this.Header = header;
        this.oid = oid;
        this.operator = operator;
        this.opTypeId = opTypeId;
        this.opType = opType;
        this.roleCommission = roleCommission;
        this.max = max;
        this.min = min;
        this.commSettingType = commSettingType;
    }

    public int getOpTypeId() {
        return opTypeId;
    }

    public int getOid() {
        return oid;
    }

    public String getOperator() {
        return operator;
    }

    public String getOpType() {
        return opType;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public int getMin() {
        return min;
    }

    public int getCommSettingType() {
        return commSettingType;
    }

    public int getMax() {
        return max;
    }

    public ArrayList<RoleCommission> getRoleCommission() {
        return roleCommission;
    }
}
