package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 16,December,2019
 */
public class AllFilterData {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("filterLists")
    @Expose
    public ArrayList<ProductInfoFilter> filterLists = null;
    @SerializedName("categoryId")
    @Expose
    public int categoryId;

    public String get$id() {
        return $id;
    }

    public ArrayList<ProductInfoFilter> getFilterLists() {
        return filterLists;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
