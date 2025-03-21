package com.solution.app.justpay4u.Api.Networking.Object;

public class BusinessData {

    public Integer id;
    public String name;
    public String displayName;
    public String url;
    public boolean isActive;
    double business;

    public BusinessData(Integer id, String name, String displayName, String url, boolean isActive, double business) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.url = url;
        this.isActive = isActive;
        this.business = business;
    }

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

    public double getBusiness() {
        return business;
    }
}
