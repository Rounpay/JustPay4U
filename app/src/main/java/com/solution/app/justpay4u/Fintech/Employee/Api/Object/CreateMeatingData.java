package com.solution.app.justpay4u.Fintech.Employee.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateMeatingData {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("purpuse")
    @Expose
    public List<CreateMeatingPurpuse> purpuse = null;
    @SerializedName("resaon")
    @Expose
    public List<CreateMeatingResaon> resaon = null;

    public int getId() {
        return id;
    }

    public List<CreateMeatingPurpuse> getPurpuse() {
        return purpuse;
    }

    public List<CreateMeatingResaon> getResaon() {
        return resaon;
    }
}
