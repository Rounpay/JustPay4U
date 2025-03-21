package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 13,December,2019
 */
public class DashbaordInfoTopCategory {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName(value = "categoryID", alternate = "categoryId")
    @Expose
    public int categoryID;
    @SerializedName(value = "mainCategoryID", alternate = "mainCategoryId")
    @Expose
    public int mainCategoryID;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName(value = "subcategory", alternate = "subCategory")
    @Expose
    public ArrayList<DashbaordInfoSubCategoryList> subcategory = null;

    public String get$id() {
        return $id;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getMainCategoryID() {
        return mainCategoryID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<DashbaordInfoSubCategoryList> getSubcategory() {
        return subcategory;
    }
}
