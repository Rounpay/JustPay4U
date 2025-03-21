package com.solution.app.justpay4u.Api.Shopping.Object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FilterList implements Serializable {
    public Boolean isSelected = false;
    @SerializedName(value = "FilterID", alternate = {"filterId", "filterID"})
    public String FilterID;
    @SerializedName(value = "ID", alternate = {"id", "Id"})
    public String ID;
    @SerializedName(value = "Name", alternate = "name")
    public String Name;

    public String getFilterID() {
        return FilterID;
    }

    public void setFilterID(String filterID) {
        FilterID = filterID;
    }

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

    public Boolean getSelected() {

        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
