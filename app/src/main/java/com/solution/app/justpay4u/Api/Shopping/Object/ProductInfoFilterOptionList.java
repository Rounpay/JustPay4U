package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vishnu Agarwal on 14,December,2019
 */
public class ProductInfoFilterOptionList implements Serializable {
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("posid")
    @Expose
    public int posid;
    @SerializedName("optionName")
    @Expose
    public String optionName;
    @SerializedName("frontImage_200")
    @Expose
    public String image;
    @SerializedName("isSelected")
    @Expose
    public boolean isSelected;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosid() {
        return posid;
    }

    public void setPosid(int posid) {
        this.posid = posid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
