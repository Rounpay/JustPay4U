package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 14,December,2019
 */
public class ProductInfoFilter implements Serializable {
    public String SearchText;
    public Boolean isSelected = false;
    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("option_Lists")
    @Expose
    public ArrayList<ProductInfoFilterOptionList> optionLists = null;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ProductInfoFilterOptionList> getOptionLists() {
        return optionLists;
    }

    public void setOptionLists(ArrayList<ProductInfoFilterOptionList> optionLists) {
        this.optionLists = optionLists;
    }

    public String getSearchText() {
        return SearchText;
    }

    public void setSearchText(String searchText) {
        SearchText = searchText;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
