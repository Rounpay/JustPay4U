package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.NewArrivalAndSaleData;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class NewArrivalAndSaleDataResponse {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public NewArrivalAndSaleData newArrivalAndSaleData;

    public String get$id() {
        return $id;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public NewArrivalAndSaleData getNewArrivalAndSaleData() {
        return newArrivalAndSaleData;
    }
}
