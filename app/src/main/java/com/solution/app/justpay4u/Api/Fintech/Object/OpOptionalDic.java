package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpOptionalDic {
    @SerializedName("optionalID")
    @Expose
    public int optionalID;
    @SerializedName("value")
    @Expose
    public String value;

    public int getOptionalID() {
        return optionalID;
    }

    public String getValue() {
        return value;
    }
}
