package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 20,December,2019
 */
public class PincodeResponseData {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("district")
    @Expose
    public String district;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("area")
    @Expose
    public ArrayList<String> area = null;

    public String get$id() {
        return $id;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public ArrayList<String> getArea() {
        return area;
    }
}
