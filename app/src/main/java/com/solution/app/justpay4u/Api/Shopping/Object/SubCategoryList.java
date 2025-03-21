package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubCategoryList implements Serializable {

    @SerializedName(value = "ID", alternate = {"id", "Id"})
    @Expose
    private int iD;
    @SerializedName(value = "Value", alternate = "value")
    @Expose
    private String value;
    @SerializedName(value = "Image", alternate = "image")
    @Expose
    private String image;

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

    public int getiD() {
        return iD;
    }

    public String getImage() {
        return image;
    }
}
