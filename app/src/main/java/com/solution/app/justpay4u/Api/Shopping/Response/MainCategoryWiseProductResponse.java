package com.solution.app.justpay4u.Api.Shopping.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.app.justpay4u.Api.Shopping.Object.DashboardProductListData;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 14,December,2019
 */
public class MainCategoryWiseProductResponse {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("totalSum")
    @Expose
    public int totalSum;


    @SerializedName("data")
    @Expose
    public ArrayList<DashboardProductListData> mainCategoryWiseProductData = null;

    public String get$id() {
        return $id;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<DashboardProductListData> getMainCategoryWiseProductData() {
        return mainCategoryWiseProductData;
    }
}
