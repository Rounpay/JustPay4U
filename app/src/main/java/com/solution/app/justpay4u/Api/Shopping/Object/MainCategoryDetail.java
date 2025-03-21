package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainCategoryDetail {

    @SerializedName(value = "ID", alternate = {"id", "Id"})
    @Expose
    private Integer iD;
    @SerializedName(value = "Category", alternate = "category")
    @Expose
    private Integer category;
    @SerializedName(value = "Name", alternate = "name")
    @Expose
    private String name;
    @SerializedName(value = "Image", alternate = "image")
    @Expose
    private String image;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
