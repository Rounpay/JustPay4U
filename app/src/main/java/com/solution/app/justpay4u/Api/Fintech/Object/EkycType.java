package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 17/01/2022.
 */
public class EkycType {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("ekycStep")
    @Expose
    private String ekycStep;

    public int getId() {
        return id;
    }

    public String getEkycStep() {
        return ekycStep;
    }


}
