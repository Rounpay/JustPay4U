package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatesCities {
    @SerializedName(value = "stateID", alternate = "stateId")
    @Expose
    public int stateID;
    @SerializedName("stateName")
    @Expose
    public String stateName;
    @SerializedName(value = "cityID", alternate = "cityId")
    @Expose
    public int cityID;
    @SerializedName("cityName")
    @Expose
    public String cityName;

    public int getStateID() {
        return stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public int getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }
}
