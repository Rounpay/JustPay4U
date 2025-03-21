package com.solution.app.justpay4u.Api.Fintech.Request;

public class FundRequestToUsers {
    private String parentName;
    private int parentID;
    private int parentRoleID;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getParentRoleID() {
        return parentRoleID;
    }

    public void setParentRoleID(int parentRoleID) {
        this.parentRoleID = parentRoleID;
    }
}
