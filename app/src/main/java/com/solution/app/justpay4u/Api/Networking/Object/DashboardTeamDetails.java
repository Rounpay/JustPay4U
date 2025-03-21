package com.solution.app.justpay4u.Api.Networking.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardTeamDetails {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("displayName")
    @Expose
    public String displayName;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;




    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrl() {
        return url;
    }

    public boolean isActive() {
        return isActive;
    }
}
