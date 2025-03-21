package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slabs {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("slab")
    @Expose
    public String slab;
    @SerializedName("isRealSlab")
    @Expose
    public boolean isRealSlab;
    @SerializedName("entryDate")
    @Expose
    public Object entryDate;
    @SerializedName("modifyDate")
    @Expose
    public Object modifyDate;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;
    @SerializedName("remark")
    @Expose
    public Object remark;
    @SerializedName("isAdminDefined")
    @Expose
    public boolean isAdminDefined;

    public int getId() {
        return id;
    }

    public String getSlab() {
        return slab;
    }

    public boolean getRealSlab() {
        return isRealSlab;
    }

    public Object getEntryDate() {
        return entryDate;
    }

    public Object getModifyDate() {
        return modifyDate;
    }

    public boolean getActive() {
        return isActive;
    }

    public Object getRemark() {
        return remark;
    }

    public boolean getAdminDefined() {
        return isAdminDefined;
    }
}
