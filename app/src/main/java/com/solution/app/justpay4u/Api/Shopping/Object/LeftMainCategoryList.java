package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LeftMainCategoryList {

    @SerializedName(value = "ID", alternate = {"id", "Id"})
    @Expose
    private int iD;
    @SerializedName(value = "Value", alternate = "value")
    @Expose
    private String value;
    @SerializedName(value = "Image", alternate = "image")
    @Expose
    private String image;
    @SerializedName(value = "CategoryList", alternate = "categoryList")
    @Expose
    private ArrayList<LeftCategoryList> categoryList = null;

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<LeftCategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<LeftCategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    public String getImage() {
        return image;
    }
}
