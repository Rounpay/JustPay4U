package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 16,December,2019
 */
public class AllProductListData {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("productSetList")
    @Expose
    public ArrayList<DashboardProductListData> productSetList = null;
    @SerializedName("totalRecords")
    @Expose
    public int totalRecords;
    @SerializedName("currentIndex")
    @Expose
    public int currentIndex;
    @SerializedName("pageLimitIndex")
    @Expose
    public int pageLimitIndex;

    public String get$id() {
        return $id;
    }

    public ArrayList<DashboardProductListData> getProductSetList() {
        return productSetList;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getPageLimitIndex() {
        return pageLimitIndex;
    }
}
