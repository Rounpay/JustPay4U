package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 18,December,2019
 */
public class OrderDetailData {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("orderAddress")
    @Expose
    public AddressData orderAddress;
    @SerializedName("orderList")
    @Expose
    public ArrayList<OrderList> orderList = null;
    @SerializedName("dict")
    @Expose
    public OrderListData dict;

    public String get$id() {
        return $id;
    }

    public AddressData getOrderAddress() {
        return orderAddress;
    }

    public ArrayList<OrderList> getOrderList() {
        return orderList;
    }

    public OrderListData getDict() {
        return dict;
    }
}
