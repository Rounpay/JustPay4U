package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class LeftCategoryList implements Serializable {

    @SerializedName(value = "ID", alternate = {"id", "Id"})
    @Expose
    private Integer iD;
    @SerializedName(value = "Value", alternate = "value")
    @Expose
    private String value;
    @SerializedName(value = "Image", alternate = "image")
    @Expose
    private String image;
    @SerializedName(value = "SubCategoryList", alternate = "subCategoryList")
    @Expose
    private ArrayList<SubCategoryList> subCategoryList = null;

    public LeftCategoryList(int iD, String value, ArrayList<SubCategoryList> subCategoryList) {
        this.iD = iD;
        this.value = value;
        this.subCategoryList = subCategoryList;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<SubCategoryList> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(ArrayList<SubCategoryList> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public Integer getiD() {
        return iD;
    }

    public String getImage() {
        return image;
    }
}
