package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.AllFilterData;

/**
 * Created by Vishnu Agarwal on 16,December,2019
 */
public class GetAllFilterResponse {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public AllFilterData data;

    public String get$id() {
        return $id;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public AllFilterData getData() {
        return data;
    }
}
