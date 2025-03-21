package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishnu Agarwal on 14,December,2019
 */
public class ProductInfoSpecificFeature {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("key")
    @Expose
    public String key;
    @SerializedName("value")
    @Expose
    public String value;

    public String get$id() {
        return $id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
