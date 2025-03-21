package com.solution.app.justpay4u.Util.DropdownDialog;

public class DropDownModel {

    String name;
    Object dataObject;
    boolean isWithdraw;
    boolean isFrenchies;

    public DropDownModel(String name, Object dataObject) {
        this.name = name;
        this.dataObject = dataObject;
    }
    public DropDownModel(String name, Object dataObject,boolean isFrenchies) {
        this.name = name;
        this.dataObject = dataObject;
        this.isFrenchies = isFrenchies;
    }
    public DropDownModel(String name, boolean isWithdraw,Object dataObject) {
        this.name = name;
        this.isWithdraw = isWithdraw;
        this.dataObject = dataObject;
    }

    public String getName() {
        return name;
    }

    public Object getDataObject() {
        return dataObject;
    }
}
