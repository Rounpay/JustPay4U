package com.solution.app.justpay4u.Api.Fintech.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsContent {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("newsDetail")
    @Expose
    public String newsDetail;
    @SerializedName("roleId")
    @Expose
    public int roleId;
    @SerializedName("createDate")
    @Expose
    public String createDate;
    @SerializedName("roleName")
    @Expose
    public String roleName;
    @SerializedName("roleSlab")
    @Expose
    public String roleSlab;
    @SerializedName("role")
    @Expose
    public String role;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNewsDetail() {
        return newsDetail;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleSlab() {
        return roleSlab;
    }

    public String getRole() {
        return role;
    }
}
