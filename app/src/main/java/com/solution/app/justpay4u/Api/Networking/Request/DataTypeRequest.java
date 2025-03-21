package com.solution.app.justpay4u.Api.Networking.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataTypeRequest {

    String FromDate;
    String ToDate;


    public DataTypeRequest(String FromDate, String ToDate) {
        this.FromDate = FromDate;
        this.ToDate = ToDate;
    }
}
