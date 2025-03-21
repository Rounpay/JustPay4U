package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Filter implements Serializable {

    public String SearchText;
    public Boolean isSelected = false;
    @SerializedName(value = "ID", alternate = {"id", "Id"})
    @Expose
    public String ID;
    @SerializedName(value = "Name", alternate = "name")
    @Expose
    public String Name;
    @SerializedName(value = "OList", alternate = "oList")
    @Expose
    public ArrayList<FilterList> OList;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<FilterList> getOList() {
        return OList;
    }

    public void setOList(ArrayList<FilterList> OList) {
        this.OList = OList;
    }

    public Boolean getSelected() {

        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getSearchText() {
        return SearchText;
    }

    public void setSearchText(String searchText) {
        SearchText = searchText;
    }
}
