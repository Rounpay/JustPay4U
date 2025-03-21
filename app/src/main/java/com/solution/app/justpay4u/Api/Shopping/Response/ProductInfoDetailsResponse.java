package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.ProductInfoDetailsData;

/**
 * Created by Vishnu Agarwal on 14,December,2019
 */
public class ProductInfoDetailsResponse {
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
    public ProductInfoDetailsData data;

    public String get$id() {
        return $id;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ProductInfoDetailsData getProductDetail() {
        return data;
    }
}
