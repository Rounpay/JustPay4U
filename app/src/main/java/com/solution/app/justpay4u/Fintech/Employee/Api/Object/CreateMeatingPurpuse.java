package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateMeatingPurpuse {
    @SerializedName("purpuseID")
    @Expose
    public int purpuseID;
    @SerializedName("purpuseDetail")
    @Expose
    public String purpuseDetail;

    public int getPurpuseID() {
        return purpuseID;
    }

    public String getPurpuseDetail() {
        return purpuseDetail;
    }
}
