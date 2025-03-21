package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class NewArrivalAndSaleData {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("newArrivals")
    @Expose
    public ArrayList<DashboardProductListData> newArrivals = null;
    @SerializedName("onSale")
    @Expose
    public ArrayList<DashboardProductListData> onSale = null;
    @SerializedName("bestSellerList")
    @Expose
    public ArrayList<DashboardProductListData> bestSellerList = null;

    public String get$id() {
        return $id;
    }

    public ArrayList<DashboardProductListData> getNewArrivals() {
        return newArrivals;
    }

    public ArrayList<DashboardProductListData> getOnSale() {
        return onSale;
    }

    public ArrayList<DashboardProductListData> getBestSellerList() {
        return bestSellerList;
    }
}
