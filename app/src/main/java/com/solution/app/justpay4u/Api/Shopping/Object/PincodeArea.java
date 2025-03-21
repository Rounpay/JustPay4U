package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PincodeArea {
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("area")
    @Expose
    public String area;
    @SerializedName("districtname")
    @Expose
    public String districtname;
    @SerializedName("statename")
    @Expose
    public String statename;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("reachInHour")
    @Expose
    public double reachInHour;
    @SerializedName("expectedDeliverInDays")
    @Expose
    public double expectedDeliverInDays;
    @SerializedName("stateId")
    @Expose
    public int stateId;
    @SerializedName("cityId")
    @Expose
    public int cityId;

    @SerializedName("isDeliveryOff")
    @Expose
    public boolean isDeliveryOff;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getDistrictname() {
        return districtname;
    }

    public String getStatename() {
        return statename;
    }

    public String getPincode() {
        return pincode;
    }

    public int getId() {
        return id;
    }

    public double getReachInHour() {
        return reachInHour;
    }

    public int getStateID() {
        return stateId;
    }

    public int getCityID() {
        return cityId;
    }

    public double getExpectedDeliverInDays() {
        return expectedDeliverInDays;
    }

    public boolean isDeliveryOff() {
        return isDeliveryOff;
    }
}
